package hellofx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;

import javax.imageio.ImageIO;
import javax.naming.directory.InvalidAttributesException;

import org.apache.commons.io.IOUtils;
import org.curiousworks.BlueMarble;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BlueMarbleController {

	@FXML
	private ImageView image;

	@FXML
	private DatePicker datePicker;

	@FXML
	private CheckBox chkEnhaced;

	@FXML
	private CheckBox chkBW;

	@FXML
	void updateDate(ActionEvent event) {
		LocalDate nowDate = LocalDate.now();
		LocalDate imageDate = LocalDate.of(datePicker.getValue().getYear(), datePicker.getValue().getMonthValue(), datePicker.getValue().getDayOfMonth());
		try {
			if(imageDate.isAfter(nowDate)) throw  new InvalidAttributesException();
			BlueMarble blueMarble = new BlueMarble();
			if((datePicker.getValue().getYear() > 2018  || (datePicker.getValue().getYear() == 2018 && datePicker.getValue().getMonthValue() > 6)) && chkEnhaced.isSelected()) {
				chkEnhaced.setSelected(false);
			}
			blueMarble.setDate(datePicker.getValue().getYear() + "-0" + datePicker.getValue().getMonthValue() + "-" + datePicker.getValue().getDayOfMonth());
	//		blueMarble.setDate("2018-0" + datePicker.getValue().getMonthValue() + "-" + datePicker.getValue().getDayOfMonth());
			blueMarble.setEnhanced(chkEnhaced.isSelected());
	//		Image value = new Image(BlueMarble.getMostRecentImage());
			image.setImage(new Image(blueMarble.getImage()));
			
			if(chkBW.isSelected()) {
				ColorAdjust colorAdj = new ColorAdjust();
				colorAdj.setSaturation(-1);
				colorAdj.setContrast(1);
				image.setEffect(colorAdj);
			}
		}catch(InvalidAttributesException iae) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Wrong Date!");
			alert.setContentText("The selected date is in the future!");
			alert.showAndWait();
		}catch(Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.showAndWait();
		}
		
	}

}
