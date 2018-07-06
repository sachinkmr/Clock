package assignment.clocks.analog;

import assignment.clocks.Clock;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.ZoneId;

public class AnalogClock extends Clock {
    @FXML
    private Circle clockFace;
    @FXML
    private Circle origin;

    protected Line hourHand;
    protected Line minuteHand;
    protected Line secondHand;
    protected Timeline secondTime, minuteTime, hourTime;

    @Override
    public void startClock() {
        origin.toFront();
        start();
    }

    @Override
    public void paintClockFace() {
        paintDots();
        paintNumbers();
    }

    @Override
    public void drawHands() {
        Point2D endPoint = pointFromOrigin(0, clockFace.getRadius() * Factor.HOUR_HAND.value());
        hourHand = createHand(endPoint, 3);

        endPoint = pointFromOrigin(0, clockFace.getRadius() * Factor.MINUTE_HAND.value());
        minuteHand = createHand(endPoint, 3);

        endPoint = pointFromOrigin(0, clockFace.getRadius() * Factor.SECOND_HAND.value());
        secondHand = createHand(endPoint, 2);
        secondHand.setStartY(clockFace.getLayoutY() + clockFace.getRadius() * Factor.SECOND_HAND_BACK_LENGTH.value());

        clockPane.getChildren().addAll(hourHand, minuteHand, secondHand);
        bindColorProperties();
    }

    protected void bindColorProperties() {
        hourHand.strokeProperty().bind(this.hourColorProperty());
        minuteHand.strokeProperty().bind(this.minuteColorProperty());
        secondHand.strokeProperty().bind(this.secondColorProperty());
        origin.fillProperty().bind(this.faceColorProperty());
        clockFace.strokeProperty().bind(this.faceColorProperty());
        clockFace.fillProperty().bind(this.bgColorProperty());
    }

    protected Line createHand(Point2D endPoint, double strokeWidth) {
        Line line = new Line();
        line.setStartX(clockFace.getLayoutX());
        line.setStartY(clockFace.getLayoutY());
        line.setEndX(endPoint.getX());
        line.setEndY(endPoint.getY());
        line.setStrokeWidth(strokeWidth);
        line.toFront();
        return line;
    }

    protected void paintDots() {
        double dotsPadding = clockFace.getRadius() * Factor.DOTS_PADDING.value();
        for (int i = 1; i <= 60; i++) {
            Point2D location = this.pointFromOrigin(i, clockFace.getRadius() - dotsPadding);
            double dotRadius = i % 5 == 0 ? 3.5 : 1.5;
            Circle dot = new Circle(location.getX(), location.getY(), dotRadius);
            dot.fillProperty().bind(this.faceColorProperty());
            clockPane.getChildren().add(dot);
        }
    }

    protected void paintNumbers() {
        double numberPadding = clockFace.getRadius() * Factor.NUMBER_PADDING.value();
        for (int i = 0; i < 60; i += 5) {
            Point2D location = this.pointFromOrigin(i, clockFace.getRadius() - numberPadding);
            Text text = new Text(Integer.toString(i / 5 == 0 ? 12 : i / 5));
            text.setX(location.getX());
            text.setY(location.getY());
            text.setLayoutX(-5);
            text.setLayoutY(5);
            text.fillProperty().bind(this.faceColorProperty());
            text.setFont(Font.font(text.getFont().getFamily(), FontWeight.BOLD, text.getFont().getSize() + 1));
            clockPane.getChildren().add(text);
        }
    }

    protected Point2D pointFromOrigin(int angle, double distanceFromOrigin) {
        double t = 2 * Math.PI * (angle - 15) / 60;
        double x = clockPane.getPrefWidth() / 2 + distanceFromOrigin * Math.cos(t);
        double y = clockPane.getPrefHeight() / 2 + distanceFromOrigin * Math.sin(t);
        return new Point2D(x, y);
    }

    protected enum Factor {
        DOTS_PADDING(0.055),
        NUMBER_PADDING(0.125),
        SECOND_HAND_BACK_LENGTH(.1315),
        HOUR_HAND(0.6),
        MINUTE_HAND(0.9),
        SECOND_HAND(0.95);

        private final double factor;

        Factor(double factor) {
            this.factor = factor;
        }

        public double value() {
            return this.factor;
        }
    }

    public void start() {

        // define rotations to map the analogueClock to the current time.
        final Rotate hourRotate = new Rotate(getHourAngle(), clockFace.getLayoutX(), clockFace.getLayoutY());
        final Rotate minuteRotate = new Rotate(getMinuteAngle(), clockFace.getLayoutX(), clockFace.getLayoutY());
        final Rotate secondRotate = new Rotate(getSecondAngle(), clockFace.getLayoutX(), clockFace.getLayoutY());
        hourHand.getTransforms().add(hourRotate);
        minuteHand.getTransforms().add(minuteRotate);
        secondHand.getTransforms().add(secondRotate);

        // the hour hand rotates twice a day.
        hourTime = new Timeline(
                new KeyFrame(
                        Duration.hours(12),
                        new KeyValue(
                                hourRotate.angleProperty(),
                                360 + getHourAngle()
                        )
                )
        );

        // the minute hand rotates once an hour.
        minuteTime = new Timeline(
                new KeyFrame(
                        Duration.minutes(60),
                        new KeyValue(
                                minuteRotate.angleProperty(),
                                360 + getMinuteAngle()
                        )
                )
        );

        // move second hand rotates once a minute.
        secondTime = new Timeline(
                new KeyFrame(
                        Duration.seconds(60),
                        new KeyValue(
                                secondRotate.angleProperty(),
                                360 + getSecondAngle()
                        )
                )
        );


        hourTime.setCycleCount(Animation.INDEFINITE);
        minuteTime.setCycleCount(Animation.INDEFINITE);
        secondTime.setCycleCount(Animation.INDEFINITE);
        secondTime.play();
        minuteTime.play();
        hourTime.play();

    }

    public double getSecondAngle() {
        return LocalTime.now(ZoneId.systemDefault()).getSecond() * (360 / 60);
    }

    public double getMinuteAngle() {
        return (LocalTime.now(ZoneId.systemDefault()).getMinute() + getSecondAngle() / 360.0) * (360 / 60);
    }

    public double getHourAngle() {
        return (Math.abs(LocalTime.now(ZoneId.systemDefault()).getHour() - 12) + getMinuteAngle() / 360.0) * (360 / 12);
    }

    public Circle getClockFace() {
        return clockFace;
    }
}
