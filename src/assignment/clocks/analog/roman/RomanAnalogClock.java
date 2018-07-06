package assignment.clocks.analog.roman;

import assignment.clocks.analog.AnalogClock;
import assignment.utils.RomanUtils;
import javafx.geometry.Point2D;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class RomanAnalogClock extends AnalogClock {
    @Override
    public void paintNumbers() {
        double numberPadding = this.getClockFace().getRadius() * Factor.NUMBER_PADDING.value();
        for (int i = 0; i < 60; i += 5) {
            Point2D location = this.pointFromOrigin(i, this.getClockFace().getRadius() - numberPadding);
            Text text = new Text(RomanUtils.toRoman(i / 5 == 0 ? 12 : i / 5));
            text.setX(location.getX());
            text.setY(location.getY());
            text.setLayoutX(-5);
            text.setLayoutY(5);
            text.fillProperty().bind(this.faceColorProperty());
            text.setFont(Font.font(text.getFont().getFamily(), FontWeight.BOLD, text.getFont().getSize() + 1));
            clockPane.getChildren().add(text);
        }
    }
}
