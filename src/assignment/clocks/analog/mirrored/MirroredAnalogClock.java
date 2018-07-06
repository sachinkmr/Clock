package assignment.clocks.analog.mirrored;

import assignment.clocks.analog.AnalogClock;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class MirroredAnalogClock extends AnalogClock {

    @Override
    public void paintNumbers() {
        double numberPadding = this.getClockFace().getRadius() * Factor.NUMBER_PADDING.value();
        for (int i = 0, x = 60; i < 60; i += 5, x -= 5) {
            Point2D location = this.pointFromOrigin(i, this.getClockFace().getRadius() - numberPadding);

            Text text = new Text(Integer.toString(x / 5 == 0 ? 12 : x / 5));
            text.setX(location.getX());
            text.setY(location.getY());
            text.setLayoutX(-5);
            text.setLayoutY(5);
            text.fillProperty().bind(this.faceColorProperty());
            text.setFont(Font.font(text.getFont().getFamily(), FontWeight.BOLD, text.getFont().getSize() + 1));
            clockPane.getChildren().add(text);
        }
    }

    public void start() {
        // determine the starting time.
// define rotations to map the analogueClock to the current time.
        final Rotate hourRotate = new Rotate(360-getHourAngle(), getClockFace().getLayoutX(), getClockFace().getLayoutY());
        final Rotate minuteRotate = new Rotate(360-getMinuteAngle(), getClockFace().getLayoutX(), getClockFace().getLayoutY());
        final Rotate secondRotate = new Rotate(360-getSecondAngle(), getClockFace().getLayoutX(), getClockFace().getLayoutY());
        hourHand.getTransforms().add(hourRotate);
        minuteHand.getTransforms().add(minuteRotate);
        secondHand.getTransforms().add(secondRotate);

        // the hour hand rotates twice a day.
        hourTime = new Timeline(
                new KeyFrame(
                        Duration.hours(12),
                        new KeyValue(
                                hourRotate.angleProperty(),
                                360-getHourAngle()
                        )
                )
        );

        // the minute hand rotates once an hour.
        minuteTime = new Timeline(
                new KeyFrame(
                        Duration.minutes(60),
                        new KeyValue(
                                minuteRotate.angleProperty(),
                                360-getMinuteAngle()
                        )
                )
        );

        // move second hand rotates once a minute.
        secondTime = new Timeline(
                new KeyFrame(
                        Duration.seconds(60),
                        new KeyValue(
                                secondRotate.angleProperty(),
                                360-getSecondAngle()
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

//    public double getSecondAngle() {
//        return 360-super.getSecondAngle();
//    }
//
//    public double getMinuteAngle() {
//        return 360-super.getMinuteAngle();
//    }
//
//    public double getHourAngle() {
//        return 360-super.getHourAngle();
//    }
}
