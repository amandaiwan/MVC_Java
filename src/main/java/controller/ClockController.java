package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import model.dao.Clock;
import model.service.ClockService;
import view.ClockView;

public class ClockController {

    private ClockService model;
    private ClockView view;

    private int picIndex = 0;
    int likes = 0;
    int dislikes = 0;
    ObservableList<Clock> clocksObservableList;


    public ClockController(ClockService model, ClockView view) {
        this.model = model;
        this.view = view;
        ControlViewVoteHandler controlViewVoteHandler = new ControlViewVoteHandler();
        ControlViewSwipeHandler controlViewSwipeHandler = new ControlViewSwipeHandler();
        ControlAddToFavoriteHandler controlAddToFavoriteHandler = new ControlAddToFavoriteHandler();
        ControlShowAllSavedFavoritesHandler controlShowAllSavedFavoritesHandler = new ControlShowAllSavedFavoritesHandler();
        updateView();
    }

    private void updateView() {

        clocksObservableList = FXCollections.observableArrayList(model.findAllProducts());
        view.getTableView().setItems(clocksObservableList);

        try {

        view.getYesCheckBox().setSelected(false);
        view.getImageView().setImage(new Image(clocksObservableList.get(getPicIndex()).getPicUrl()));
        view.getPicTextLabel().setText(clocksObservableList.get(getPicIndex()).getPicText());
        view.getLikeLabel().setText(String.valueOf(clocksObservableList.get(getPicIndex()).getLikes()));
        view.getDislikeLabel().setText(String.valueOf(clocksObservableList.get(getPicIndex()).getDislikes()));

        } catch (Exception e) {
            view.getImageView().setImage(new Image("https://static.thenounproject.com/png/75231-200.png"));
            System.out.println("Kunde inte ladda någon bild...");
        }
    }

    public ClockView getView() {
        return view;
    }

    private class ControlViewVoteHandler implements EventHandler{

        public ControlViewVoteHandler() {
            view.addActionToLikeBtn(this);
            view.addActionToDislikeBtn(this);
        }

        @Override
        public void handle(Event event) {

            Button btn = (Button) event.getSource();
            Clock product = getClocksObservableList().get(getPicIndex());

            likes = product.getLikes();
            dislikes = product.getDislikes();

            if(btn == view.getLikeBtn()){
                likes++;
                product.setLikes(likes);
                model.updateProduct(product);
            }

            if(btn == view.getDislikeBtn()){
                dislikes++;
                product.setDislikes(dislikes);
                model.updateProduct(product);
            }
            updateView();
        }
    }

    private class ControlViewSwipeHandler implements EventHandler{

        public ControlViewSwipeHandler() {
            view.addActionToSwipeLeftBtn(this);
            view.addActionToSwipeRightBtn(this);
        }

        @Override
        public void handle(Event event) {
            Button btn = (Button) event.getSource();

            if (btn == view.getRightBtn()) {
                picIndex++;
                if (picIndex > getClocksObservableList().size()-1) {
                    picIndex = getClocksObservableList().size()-1;
                }
            }

            if (btn == view.getLeftBtn()) {
                picIndex--;
                if (picIndex < 0) {
                    picIndex = 0;
                }
            }

            updateView();

        }
    }

    private class ControlAddToFavoriteHandler implements EventHandler{

        public ControlAddToFavoriteHandler() {
            view.addActionToFavoriteCheckBox(this);
        }

        @Override
        public void handle(Event event) {

            CheckBox checkBox = (CheckBox) event.getSource();


            Clock product = getClocksObservableList().get(getPicIndex());

            if (checkBox.isSelected() == view.getYesCheckBox().isSelected()){
                model.addToFavorites(product);
            }
        }
    }

    private class ControlShowAllSavedFavoritesHandler implements EventHandler{

        public ControlShowAllSavedFavoritesHandler() {
            view.addActionShowAllFavorites(this);
        }

        @Override
        public void handle(Event event) {
            MenuItem menuItem = (MenuItem) event.getSource();

            if(menuItem == view.getMenuSaveToTextFileItem()){
                sortOnFavorites();
            }
        }
    }

    private void sortOnFavorites() {

        ObservableList<Clock> savedFavorites = FXCollections.observableArrayList(model.showAllFavoriteClocks());

        view.getTableView().setItems(savedFavorites);

    }

    public int getPicIndex() {
        return picIndex;
    }

    public ObservableList<Clock> getClocksObservableList() {
        return clocksObservableList;
    }
}
