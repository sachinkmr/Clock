package assignment.clocks.digital;

import assignment.clocks.Clock;
import assignment.clocks.analog.AnalogClock;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import javax.naming.Binding;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.ResourceBundle;

public class DigitalClock extends Clock {
    @FXML
    private HBox labels;
    @FXML
    private Label hours;
    @FXML
    private Label minutes;
    @FXML
    private Label seconds;
    @FXML
    private Label colon1;
    @FXML
    private Label colon2;
    @FXML
    private Label am_pm;

    @Override
    protected void startClock() {
        // the digital clock updates once a second.
        final Timeline digitalTime = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        actionEvent -> drawHands()),
                new KeyFrame(Duration.seconds(1))
        );

        digitalTime.setCycleCount(Animation.INDEFINITE);
        digitalTime.play();
    }

    @Override
    public void paintClockFace() {
        bindColorProperties();
    }

    protected void bindColorProperties() {
        labels.backgroundProperty().setValue(new Background(new BackgroundFill(this.bgColorProperty().getValue(), null, null)));
        this.bgColorProperty().addListener((observable, oldValue, newValue) -> labels.setBackground(new Background(new BackgroundFill(this.bgColorProperty().getValue(), null, null))));
        this.faceColorProperty().addListener((observable, oldValue, newValue) -> labels.setStyle("-fx-border-color: #" + Integer.toHexString(this.faceColorProperty().getValue().hashCode())));
        hours.textFillProperty().bind(this.hourColorProperty());
        minutes.textFillProperty().bind(this.minuteColorProperty());
        seconds.textFillProperty().bind(this.secondColorProperty());
        colon1.textFillProperty().bind(this.faceColorProperty());
        colon2.textFillProperty().bind(this.faceColorProperty());
        am_pm.textFillProperty().bind(this.faceColorProperty());
    }

    @Override
    public void drawHands() {
        hours.setText(getHourString());
        minutes.setText(getMinuteString());
        seconds.setText(getSecondString());
        am_pm.setText(LocalTime.now(ZoneId.systemDefault()).getHour() < 12 ? "AM" : "PM");
    }

    protected String getHourString() {
        int hour = LocalTime.now(ZoneId.systemDefault()).getHour();
        return pad(2, hour == 0 ? 12 : hour > 12 ? hour - 12 : hour);
    }

    protected String getMinuteString() {
        return pad(2, LocalTime.now(ZoneId.systemDefault()).getMinute());
    }

    protected String getSecondString() {
        return pad(2, LocalTime.now(ZoneId.systemDefault()).getSecond());
    }

    protected String pad(int paddingCount, int s) {
        StringBuilder sb = new StringBuilder();
        String str = Integer.toString(s);
        for (int i = str.length(); i < paddingCount; i++) {
            sb.append('0');
        }
        sb.append(s);
        return sb.toString();
    }
}
