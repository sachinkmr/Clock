package assignment;

import assignment.clocks.Clock;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class LauncherController implements Initializable {
    private Map<String, Clock> clocks;

    @FXML
    private StackPane clocksPane;
    @FXML
    private Button resetAllColorsButton;
    @FXML
    private ComboBox<String> switchClockButton;
    @FXML
    private ColorPicker faceColorButton;
    @FXML
    private ColorPicker hourColorButton;
    @FXML
    private ColorPicker minuteColorButton;
    @FXML
    private ColorPicker secondColorButton;
    @FXML
    private ColorPicker bgColorButton;
    @FXML
    private ToggleButton setAlarmButton;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clocks = new HashMap<>();

        initTooltips();
        initColorButtonStyle();

        resetAllColorsButton.setOnAction((event) -> resetAllColors());
        switchClockButton.valueProperty().addListener((ov, oldClockName, newClockName) -> {
            clocks.values().forEach(clock -> clock.hideClock());
            Clock newClock = clocks.get(newClockName);
            newClock.getClockPane().toFront();
            newClock.showClock();
        });
    }

    private void bindColorsProperties(Clock clock) {
        hourColorButton.valueProperty().bindBidirectional(clock.hourColorProperty());
        minuteColorButton.valueProperty().bindBidirectional(clock.minuteColorProperty());
        secondColorButton.valueProperty().bindBidirectional(clock.secondColorProperty());
        faceColorButton.valueProperty().bindBidirectional(clock.faceColorProperty());
        bgColorButton.valueProperty().bindBidirectional(clock.bgColorProperty());
    }

    public void resetAllColors() {
        Clock clock = getCurrentClock();
        clock.resetColors();
    }

    public Clock getCurrentClock() {
        String clockName = switchClockButton.getValue() == null ? "Analog Clock" : switchClockButton.getValue();
        return clocks.get(clockName);
    }

    private void initColorButtonStyle() {
        hourColorButton.setStyle("-fx-color-label-visible: false ;");
        minuteColorButton.setStyle("-fx-color-label-visible: false ;");
        secondColorButton.setStyle("-fx-color-label-visible: false ;");
        faceColorButton.setStyle("-fx-color-label-visible: false ;");
        bgColorButton.setStyle("-fx-color-label-visible: false ;");
    }


    public void initTooltips() {
        this.switchClockButton.setTooltip(new Tooltip("Switch between clock types"));
        this.bgColorButton.setTooltip(new Tooltip("Chnage clock background color"));
        this.resetAllColorsButton.setTooltip(new Tooltip("Reset all colors to default"));
        this.setAlarmButton.setTooltip(new Tooltip("Set Alarm"));
        this.hourColorButton.setTooltip(new Tooltip("Change hour color"));
        this.minuteColorButton.setTooltip(new Tooltip("Change minute color"));
        this.secondColorButton.setTooltip(new Tooltip("Change second's color"));
        this.faceColorButton.setTooltip(new Tooltip("Change clock face color"));
    }


    public Clock registerClock(FXMLLoader loader, String name) {
        try {
            Pane clockPane = loader.load();
            clockPane.setVisible(false);
            clocksPane.getChildren().add(clockPane);
            Clock clock = loader.getController();
            clock.setName(name);

            if (!switchClockButton.getItems().contains(name)) {
                switchClockButton.getItems().add(name);
            }
            bindColorsProperties(clock);
            clocks.put(name, clock);
            return clock;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to create clock");
        }
    }

    public void unRegisterClock(Clock clock) {
        if (clocks.containsKey(clock.getName())) {
            clocksPane.getChildren().remove(clock.getClockPane());
            switchClockButton.getItems().remove(clock.getName());
            clocks.remove(clock.getName());
        }
    }
}
