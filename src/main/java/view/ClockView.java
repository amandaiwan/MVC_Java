package view;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.dao.Clock;
import model.service.ClockService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClockView extends BorderPane {

    //SwipeBtn
    private Button leftBtn = new Button("<");
    private Button rightBtn = new Button(">");

    //Labelholder för rösträknare
    private Label likeLabel = getCounterLabel();
    private Label dislikeLabel = getCounterLabel();

    //Labelholder för bildtext
    private Label picTextLabel = new Label();

    //like/unlike-Btn
    private Button likeBtn = getIconBtn("icons/baseline_thumb_up_white_18dp.png");
    private Button dislikeBtn = getIconBtn("icons/baseline_thumb_down_white_18dp.png");

    //RadioKnapparna
    private CheckBox yesCheckBox = new CheckBox();

    //I lager-input
    private ToggleGroup toggleGroup = new ToggleGroup();

    //Imageview
    private ImageView imageView = new ImageView();
    private int picIndex = 0;

    //Tableview
    private TableView<Clock> tableView = new TableView<>();
    private String[] tableColumnName = {"Id", "Bildtext ", "BildUrl", "Likes ", "Dislikes "};
    private TableColumn[] tableColumns = new TableColumn[tableColumnName.length];
    private String[] dtoProductInfo = {"id", "picText", "picUrl", "likes", "dislikes"};

    //Menuitems för menyn
    private MenuItem menuSaveToTextFileItem = new MenuItem("Visa favoriter");

    //Lista för klockor
    private ClockService service = new ClockService();
    private ObservableList<Clock> productObservableList;

    public ClockView() {
        setTop(topLayout());
        setLeft(leftLayout());
        setCenter(centerLayout());
        setRight(rightLayout());
        setBottom(bottomLayout());
    }

    private GridPane topLayout(){

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        MenuBar menuBar = new MenuBar();
        menuBar.setPrefWidth(500);
        Menu menu = new Menu("Fil");
        menu.getItems().addAll(menuSaveToTextFileItem);
        menuBar.getMenus().add(menu);

       /* Label title = new Label("Titel");
        title.setFont(new Font("Cambria",25));
        title.setTextFill(Color.WHITE);
        title.setMinWidth(Region.USE_PREF_SIZE); */

        gridPane.add(menuBar, 0, 0);

        return gridPane;

    }

    private GridPane leftLayout(){

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER_LEFT);

        HBox isFavoriteHolder = new HBox();
        isFavoriteHolder.setSpacing(2);
        isFavoriteHolder.setPadding(new Insets(5, 5, 5, 5));

        Label favoriteLabel = new Label("Lägg till favorit? ");

        favoriteLabel.setFont(Font.font("Cambria", 15));
        favoriteLabel.setTextFill(Color.WHITE);

        isFavoriteHolder.getChildren().addAll(favoriteLabel, yesCheckBox);

        gridPane.add(isFavoriteHolder, 0 , 0);

        return gridPane;

    }

    private GridPane centerLayout(){

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER_LEFT);
        gridPane.setPadding(new Insets(5, 5, 5, 20));

        HBox picHolder = new HBox();
        picHolder.setSpacing(10);
        picHolder.setPadding(new Insets(5, 5, 5, 5));

        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        picHolder.getChildren().add(imageView);

        HBox picTextHolder = new HBox();
        picTextHolder.setPadding(new Insets(5, 0, 0, 0));
        picTextHolder.setAlignment(Pos.CENTER);
        picTextHolder.getChildren().add(picTextLabel);

        picTextLabel.setFont(Font.font("Cambria", 15));
        picTextLabel.setTextFill(Color.WHITE);

        HBox btnHolder = new HBox();
        btnHolder.setSpacing(10);
        btnHolder.setAlignment(Pos.CENTER);
        btnHolder.getChildren().addAll(leftBtn, rightBtn);


        gridPane.add(picTextHolder, 0, 0);
        gridPane.add(picHolder, 0, 1);
        gridPane.add(btnHolder, 0, 2);

        return gridPane;

    }

    private GridPane rightLayout(){

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER_LEFT);

        VBox voteBtnHolder = new VBox();
        voteBtnHolder.setSpacing(10);
        voteBtnHolder.setPadding(new Insets(5, 10, 0, 5));
        voteBtnHolder.getChildren().addAll(likeBtn, dislikeBtn);

        VBox labelHolder = new VBox();
        labelHolder.setSpacing(20);
        labelHolder.setPadding(new Insets(15, 30, 0, 5));
        labelHolder.getChildren().addAll(likeLabel, dislikeLabel);

        gridPane.add(voteBtnHolder, 0, 0);
        gridPane.add(labelHolder, 1, 0);

        return gridPane;
    }

    private GridPane bottomLayout(){

        GridPane gridPane = new GridPane();

        HBox btnHolder = new HBox();
        btnHolder.setSpacing(20);
        btnHolder.setMinWidth(500);
        btnHolder.setPadding(new Insets(15, 5, 15, 5));
        btnHolder.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < tableColumnName.length; i++) {
            tableColumns[i] = new TableColumn(tableColumnName[i]);
            tableColumns[i].setCellValueFactory(new PropertyValueFactory<Clock, String>(dtoProductInfo[i]));
        }

        tableView.getColumns().addAll(tableColumns);
        tableView.setMaxHeight(250);
        //tog bort en extra kolumn
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setMinWidth(500);

        gridPane.add(btnHolder, 0, 0);
        gridPane.add(tableView, 0,1);

        return gridPane;
    }

    private Button getIconBtn(String stringPath) {

        Button button = null;

        try {
            BufferedImage imagePath = ImageIO.read(new File(stringPath));
            Image image = SwingFXUtils.toFXImage(imagePath, null);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(35);
            imageView.setPreserveRatio(true);
            button = new Button(null, imageView);
            button.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
            button.setPadding(Insets.EMPTY);

        } catch (IOException e) {
            Logger.getLogger(ClockView.class.getName()).log(Level.SEVERE, null, e);
        }
        return button;
    }

    private Label getCounterLabel() {
        Label label = new Label();
        label.setFont(Font.font("Cambria", 15));
        label.setTextFill(Color.WHITE);
        return label;
    }

    public Button getLeftBtn() {
        return leftBtn;
    }

    public Button getRightBtn() {
        return rightBtn;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Button getLikeBtn() {
        return likeBtn;
    }

    public Button getDislikeBtn() {
        return dislikeBtn;
    }

    public void addActionToLikeBtn(EventHandler eventHandler) {
        likeBtn.setOnAction(eventHandler);
    }

    public void addActionToDislikeBtn(EventHandler eventHandler) {
        dislikeBtn.setOnAction(eventHandler);
    }

    public void addActionToSwipeLeftBtn(EventHandler eventHandler){
        leftBtn.setOnAction(eventHandler);
    }

    public void addActionToSwipeRightBtn(EventHandler eventHandler){
        rightBtn.setOnAction(eventHandler);
    }

    public void addActionToFavoriteCheckBox(EventHandler eventHandler){
        yesCheckBox.setOnAction(eventHandler);
    }

    public void addActionShowAllFavorites(EventHandler eventHandler){
        menuSaveToTextFileItem.setOnAction(eventHandler);
    }

    public CheckBox getYesCheckBox() {
        return yesCheckBox;
    }

    public Label getLikeLabel() {
        return likeLabel;
    }

    public Label getDislikeLabel() {
        return dislikeLabel;
    }

    public TableView<Clock> getTableView() {
        return tableView;
    }

    public Label getPicTextLabel() {
        return picTextLabel;
    }

    public MenuItem getMenuSaveToTextFileItem() {
        return menuSaveToTextFileItem;
    }

}
