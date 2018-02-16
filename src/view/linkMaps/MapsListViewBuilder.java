package view.linkMaps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.map.Map;
import model.tile.Tile;
import view.mainView.MainViewController.MapCell;

public abstract class MapsListViewBuilder{
	
	public static void setContent(MapMenuController mapMenuController, ListView<Map> mapListView) {
		setListView(mapMenuController, mapListView);
	}

	private static void setListView(MapMenuController mapMenuController, ListView<Map> mapListView) {
		ArrayList<Map> MapsArrayList = new ArrayList<>();
		ObservableList<Map> MapsList = FXCollections.observableArrayList(MapsArrayList);
		
		try {
			Files.list(Paths.get("maps")).forEach(path -> {
				String fileName = path.getFileName().toString();
				Map map = new Map(fileName.substring(0, fileName.length() - 4));
				MapsList.add(map.load());
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mapListView.setItems(MapsList);

		mapListView.setCellFactory(new Callback<ListView<Map>, ListCell<Map>>() {
			@Override
			public ListCell<Map> call(ListView<Map> TilesListView) {
				return new MapCell();
			}
		});

		mapListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Map>() {
			public void changed(ObservableValue<? extends Map> observable, Map oldValue, Map newValue) {
				newValue.setGraphics(mapMenuController.getZoomSlider().getValue() * Tile.TILE_SIZE);
				mapMenuController.setMap(newValue);
			}
		});
	}
}
