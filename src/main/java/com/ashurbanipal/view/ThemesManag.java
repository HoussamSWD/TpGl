/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.view;

import com.ashurbanipal.controllers.CatalogueController;
import com.ashurbanipal.entities.Theme;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;

/**
 *
 * @author swdth
 */
@ManagedBean
@ViewScoped
public class ThemesManag {

    @EJB
    private CatalogueController controller;

    // the root of the mind map
    private MindmapNode root;
    private Theme selectedTheme = null;
    private List<Theme> themes;

    /**
     * Creates a new instance of ThemesManag
     */
    public ThemesManag() {

    }

    /**
     * create a new instance of the root and load it's children
     */
    public void initRoot() {
        // create a new root
        root = new DefaultMindmapNode("All themes", "thedata", "", false);
        // get themes without parent (main themes ) and put them as children
        List<Theme> themes = controller.getThemes(null);
        addChilren(root, themes);
    }

    /**
     * add children to a given node
     *
     * @param node node to add children
     * @param themes list of themes to add as children
     */
    public void addChilren(MindmapNode node, List<Theme> themes) {
        MindmapNode mindmapNode = null;
        for (Object theme : themes) {
            mindmapNode = new DefaultMindmapNode(((Theme) theme).getName(), ((Theme) theme), ((Theme) theme).getName(), true);
            node.addNode(mindmapNode);
        }
    }

    /**
     * init the root and fill the themes
     */
    @PostConstruct
    public void init() {
        initRoot();
        this.themes = controller.getThemes();
    }

    /**
     * action to preform when the user select a node it load the children themes
     * from the DB and display them
     *
     * @param event
     */
    public void onNodeSelect(SelectEvent event) {
        // get the selected nood from the event
        MindmapNode mindmapNode = (MindmapNode) event.getObject();
        //if it's the root we re int the mind map
        if (mindmapNode.equals(root)) {
            initRoot();
            return;
        }

        //else we lowad the children themes 
        Theme t = (Theme) mindmapNode.getData();
        selectedTheme = t;
        List<Theme> themes = controller.getThemes(t);

        //and we add them as children noods
        addChilren(mindmapNode, themes);
    }

    /**
     * create a new theme instance and set the selected theme as parent
     */
    public void prepareCreate() {
        //create a theme and set the selected theme as parent
        Theme t = new Theme();
        if (selectedTheme != null) {
            t.setParentTheme(selectedTheme);
        }

        //set the selected as the parent
        selectedTheme = t;
    }

    /**
     *create a new theme and display a success / error message 
     */
    public void createTheme() {

        FacesContext context = FacesContext.getCurrentInstance();
        try {
            controller.createTheme(selectedTheme);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            context.addMessage("hh", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "can't pesist in the data base"));
            return;
        }
        initRoot();
        context.addMessage("fhh", new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucses", "the theme is created"));
    }

    public void deleteTheme() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            controller.deleteTheme(selectedTheme);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Eror", "can't delete the theme"));
            return;
        }
        initRoot();
        selectedTheme = null;
        context.addMessage("fhh", new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucses", "the theme has been deleted"));
    }

    public void editTheme() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            controller.editTheme(selectedTheme);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Eror", "can't edit the theme"));
            return;
        }
        initRoot();
        selectedTheme = null;
        context.addMessage("fhh", new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucses", "the theme has been edited "));
    }

    // Getters and setters _____________________________________________________________
    public Theme getSelectedTheme() {
        return selectedTheme;
    }

    public void setSelectedTheme(Theme selectedTheme) {
        this.selectedTheme = selectedTheme;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public MindmapNode getRoot() {
        return root;
    }

    public void setRoot(MindmapNode root) {
        this.root = root;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

}
