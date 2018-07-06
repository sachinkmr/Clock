package assignment.clocks;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public abstract class Clock implements Initializable {
    private boolean isAlarmSet = false;

    private ObjectProperty<Color> hourColor;
    private ObjectProperty<Color> minuteColor;
    private ObjectProperty<Color> secondColor;
    private ObjectProperty<Color> faceColor;
    private ObjectProperty<Color> bgColor;

    @FXML
    protected AnchorPane clockPane;
    protected String name;

    public Clock() {
        this.hourColor = new SimpleObjectProperty<>(Color.valueOf("#6b6969"));
        this.minuteColor = new SimpleObjectProperty<>(Color.valueOf("#6b6969"));
        this.secondColor = new SimpleObjectProperty<>(Color.valueOf("red"));
        this.faceColor = new SimpleObjectProperty<>(Color.valueOf("#6b6969"));
        this.bgColor = new SimpleObjectProperty<>(Color.valueOf("white"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paintClockFace();
        drawHands();
        startClock();
    }

    protected abstract void paintClockFace();

    protected abstract void drawHands();

    protected abstract void startClock();

    public void hideClock() {
        clockPane.setVisible(false);
    }

    public void showClock() {
        clockPane.setVisible(true);
    }


    public void setName(String name) {
        this.name = name;
    }

    public void resetColors() {
        this.hourColor.setValue(Color.valueOf("#6b6969"));
        this.minuteColor.setValue(Color.valueOf("#6b6969"));
        this.secondColor.setValue(Color.valueOf("red"));
        this.faceColor.setValue(Color.valueOf("#6b6969"));
        this.bgColor.setValue(Color.valueOf("white"));
    }

    public String getName() {
        return name;
    }

    public AnchorPane getClockPane() {
        return clockPane;
    }

    public boolean isAlarmSet() {
        return isAlarmSet;
    }

    public void setAlarmSet(boolean alarmSet) {
        isAlarmSet = alarmSet;
    }


    public ObjectProperty<Color> hourColorProperty() {
        return hourColor;
    }


    public ObjectProperty<Color> minuteColorProperty() {
        return minuteColor;
    }


    public ObjectProperty<Color> secondColorProperty() {
        return secondColor;
    }

    public ObjectProperty<Color> faceColorProperty() {
        return faceColor;
    }


    public ObjectProperty<Color> bgColorProperty() {
        return bgColor;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Clock clock = (Clock) o;
        return Objects.equals(this.getName(), clock.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }
}
