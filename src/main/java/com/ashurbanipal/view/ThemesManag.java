/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.view;

import com.ashurbanipal.controllers.CatalogueController;
import com.ashurbanipal.entities.Theme;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
    
    private MindmapNode root;

    public MindmapNode getRoot() {
        return root;
    }

    public void setRoot(MindmapNode root) {
        this.root = root;
    }
    
    public void addChilren(MindmapNode node, List<Theme> themes){
        MindmapNode mindmapNode=null;
        for (Object theme : themes) {
           mindmapNode = new DefaultMindmapNode(((Theme)theme).getName(), ((Theme)theme), ((Theme)theme).getName(), true);
           node.addNode(mindmapNode);
        }
    }
    
    /**
     * Creates a new instance of ThemesManag
     */
    public ThemesManag() {
        
    }
    
    @PostConstruct
    public void init(){
       initRoot();
    }
    
    public void initRoot(){
        root = new DefaultMindmapNode("All themes", "thedata", "", false);
        
        List<Theme> themes = controller.getThemes(null);
        addChilren(root, themes);
    }
    
    public void onNodeSelect(SelectEvent event){
        System.out.println("Salut-----------------------------");
        
        MindmapNode mindmapNode = (MindmapNode)event.getObject();
        if(mindmapNode.equals(root)){
            initRoot();
            return;
        }
        
        Theme t =(Theme) mindmapNode.getData();
        List<Theme> themes=  controller.getThemes(t);
        
        addChilren(mindmapNode, themes);
        
    }
}
