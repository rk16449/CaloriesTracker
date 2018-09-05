package model;

import java.util.ArrayList;
import java.util.Date;

public class ExerciseChartDay {

	private Date currentDate;
	
	private ArrayList<ExerciseChartData> lineData = new ArrayList<ExerciseChartData>();
	
	public ExerciseChartDay(Date date) {
		this.currentDate = date;
	}
	
	public Date getDate() {
		return this.currentDate;
	}

	public ArrayList<ExerciseChartData> getLineData(){
		return this.lineData;
	}
	
	public boolean addChartData(ExerciseChartData data) {
		return lineData.add(data);
	}
	
	public boolean removeChartData(ExerciseChartData data) {
		for(int i=lineData.size()-1; i>=0; i--) {
			if(lineData.get(i).equals(data)) {
				return lineData.remove(i) != null;
			}
		}
		return false;
	}
}
