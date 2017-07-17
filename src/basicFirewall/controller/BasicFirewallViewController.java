package basicFirewall.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import basicFirewall.model.IpAddress;

public class BasicFirewallViewController {
    @FXML
    private JFXTextField whiteField;
    @FXML
    private JFXButton whiteAddBtn;
    @FXML
    private JFXButton whiteDeleteBtn;
    @FXML
    private JFXListView<Label> whiteListView;
    @FXML
    private JFXTextField blackField;
    @FXML
    private JFXButton blackAddBtn;
    @FXML
    private JFXButton BlackDeleteBtn;
    @FXML
    private JFXListView<Label> blackListView;
    
    public void initialize() throws IOException{
    	System.out.println("Initialized");
    	for(IpAddress i:IpAddress.getWhiteList()){
    		//System.out.println("White: " + i);
			Label ipAddressLbl = new Label(i.getIpAddress());
			ipAddressLbl.setMinWidth(500);
			ipAddressLbl.setPrefWidth(500);
	
			whiteListView.getItems().add(ipAddressLbl);
    	}
    	
    	for(IpAddress i:IpAddress.getBlackList()){
    		//System.out.println("Black: " + i);
			Label ipAddressLbl = new Label(i.getIpAddress());
			ipAddressLbl.setMinWidth(500);
			ipAddressLbl.setPrefWidth(500);
	
			blackListView.getItems().add(ipAddressLbl);
    	}
    }
    
    @FXML
    void addBlack(ActionEvent event) throws IOException {
    	String ipLine = blackField.getText();
    	if(!ipLine.isEmpty() && ipLine != ""){
    		IpAddress ip = new IpAddress(ipLine);
	    	Label ipAddressLbl = new Label(ip.getIpAddress());
			ipAddressLbl.setMinWidth(500);
			ipAddressLbl.setPrefWidth(500);
	    	blackListView.getItems().add(0, ipAddressLbl);
    	}
    	blackField.clear();
    	updateFile();
    }

    @FXML
    void addWhite(ActionEvent event) throws IOException {
    	String ipLine = whiteField.getText();
    	if(!ipLine.isEmpty() && ipLine != ""){
	    	Label ipAddressLbl = new Label(ipLine);
			ipAddressLbl.setMinWidth(500);
			ipAddressLbl.setPrefWidth(500);
	    	whiteListView.getItems().add(0, ipAddressLbl);
    	}
    	whiteField.clear();
    	updateFile();
    }

    @FXML
    void deleteBlack(ActionEvent event) throws IOException {
    	int selectedIndex = blackListView.getSelectionModel().getSelectedIndex();
    	blackListView.getItems().remove(selectedIndex);
    	updateFile();
    }

    @FXML
    void deleteWhite(ActionEvent event) throws IOException {
    	int selectedIndex = whiteListView.getSelectionModel().getSelectedIndex();
    	whiteListView.getItems().remove(selectedIndex);
    	updateFile();
    }
    
	void updateFile() throws IOException{
		ArrayList<Label> whiteList = new ArrayList<Label>(whiteListView.getItems());
    	ArrayList<Label> blackList = new ArrayList<Label>(blackListView.getItems());
    	String writeToFileLine = "";
    	if(whiteList.isEmpty() || blackList.isEmpty()){
    		if(whiteList.isEmpty() && blackList.isEmpty()){
    			
    		}else if(whiteList.isEmpty()){
    			for(int i = 0; i < blackList.size(); i++){
    				if(i == 0)
    					writeToFileLine += "," + blackList.get(i).getText();
    				else
    					writeToFileLine += ">" + blackList.get(i).getText();
    			}
    		}else{
    			for(int i = 0; i < whiteList.size(); i++){
        			if(i == 0)
        				writeToFileLine += whiteList.get(i).getText();
        			else
        				writeToFileLine += ">" + whiteList.get(i).getText();
        		}
    		}
    	}else{
    		for(int i = 0; i < whiteList.size(); i++){
    			if(i == 0)
    				writeToFileLine += whiteList.get(i).getText();
    			else
    				writeToFileLine += ">" + whiteList.get(i).getText();
    		}
    		for(int i = 0; i < blackList.size(); i++){
				if(i == 0)
					writeToFileLine += "," + blackList.get(i).getText();
				else
					writeToFileLine += ">" + blackList.get(i).getText();
			}
    	}
		
		IpAddress.writeToFile(writeToFileLine);
    }
}
