package CandidatsOnDelete;

import domain.Electrod;
import domain.ElectrodTree;

import domain.InitializerForTest;
import entities.Detail;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ElectrodTreeView {
    private TreeView<String> tree;
    private TreeItem<String> root;

    public ElectrodTreeView(){
        createTree();
        addChildrens(InitializerForTest.getElectrodTree());
    }

    private void createTree(){
        //empty root
        root = new TreeItem<>();
        root.setExpanded(true);

        tree = new TreeView<>(root);
        tree.setShowRoot(false);
    }

    public void addChildrens(ObservableList<ElectrodTree> electrods){
        for (ElectrodTree current: electrods){
            Electrod electrod = current.getElectrod();
            TreeItem<String> elParent = new TreeItem<>(electrod.getInfoForTree());
            root.getChildren().add(elParent);
            current.getDetails().forEach(detail -> {
                elParent.getChildren().add(new TreeItem<String>(detail.getInfoForTree()));
            });
        }
    }

    public TreeView getTree() {
        return tree;
    }
}
