package calculator;

import java.util.Map;
import java.util.TreeMap;

class Constants {
	private Constants() {
		// No-Op
	}

	public final static String YES = "Yes";
	public final static String NO = "No";
	public final static String MALE = "Male";
	public final static String FEMALE = "Female";
	public final static double MIN_PREMIUM_AMOUNT = 5000;
	public final static String HEALTH_INSURANCE_ERROR_MESSAGE = "Service GateWay Failed ! Please Retry !";
}

class PremiumForm {
	private String name;
	private String gender;
	private int age;
	private String smoking;
	private String alcohol;
	private String dailyExercise;
	private String drugs;
	private String Hypertension;
	private String bloodPressure;
	private String bloodSugar;
	private String overWeight;

	public PremiumForm(String name, String gender, int age, String smoking, String alcohol, String dailyExercise,
			String drugs, String hypertension, String bloodPressure, String bloodSugar, String overWeight) {
		super();
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.smoking = smoking;
		this.alcohol = alcohol;
		this.dailyExercise = dailyExercise;
		this.drugs = drugs;
		Hypertension = hypertension;
		this.bloodPressure = bloodPressure;
		this.bloodSugar = bloodSugar;
		this.overWeight = overWeight;
	}

	public PremiumForm() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSmoking() {
		return smoking;
	}

	public void setSmoking(String smoking) {
		this.smoking = smoking;
	}

	public String getAlcohol() {
		return alcohol;
	}

	public void setAlcohol(String alcohol) {
		this.alcohol = alcohol;
	}

	public String getDailyExercise() {
		return dailyExercise;
	}

	public void setDailyExercise(String dailyExercise) {
		this.dailyExercise = dailyExercise;
	}

	public String getDrugs() {
		return drugs;
	}

	public void setDrugs(String drugs) {
		this.drugs = drugs;
	}

	public String getHypertension() {
		return Hypertension;
	}

	public void setHypertension(String hypertension) {
		Hypertension = hypertension;
	}

	public String getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public String getBloodSugar() {
		return bloodSugar;
	}

	public void setBloodSugar(String bloodSugar) {
		this.bloodSugar = bloodSugar;
	}

	public String getOverWeight() {
		return overWeight;
	}

	public void setOverWeight(String overWeight) {
		this.overWeight = overWeight;
	}

	@Override
	public String toString() {
		return "PremiumForm [name=" + name + ", gender=" + gender + ", age=" + age + ", smoking=" + smoking
				+ ", alcohol=" + alcohol + ", dailyExercise=" + dailyExercise + ", drugs=" + drugs + ", Hypertension="
				+ Hypertension + ", bloodPressure=" + bloodPressure + ", bloodSugar=" + bloodSugar + ", overweight="
				+ overWeight + "]";
	}

}

class PremiumResponse {
	private String name;
	private double amount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "PremiumResponse [name=" + name + ", amount=" + amount + "]";
	}

}

class HealthUtil {
	private static Map<String, Integer> map = new TreeMap<>();

	static {
		map.put("18-25", 10);
		map.put("25-30", 10);
		map.put("30-35", 10);
		map.put("35-40", 10);
	}

	public double calculatePoint(int enterAge) {
		PremiumForm form = new PremiumForm();
		enterAge = form.getAge();
		double amount = Constants.MIN_PREMIUM_AMOUNT;
		int startAge = 0;
		int endAge = 0;
		double perc = 0;
		for (String age : map.keySet()) {
			startAge = splitAgeLimit(age)[0];
			endAge = splitAgeLimit(age)[1];
			if ((enterAge >= startAge && enterAge <= endAge) || (enterAge >= startAge && enterAge >= endAge)) {
				perc = (double) map.get(age) / 100;
				amount = amount + (amount * perc);
			}
		}
		return amount;
	}

	private static int[] splitAgeLimit(String age) {
		String[] s = age.split("-");
		return new int[] { Integer.valueOf(s[0]), Integer.valueOf(s[1]) };
	}
}

class CalculatePremiumService {
	HealthUtil util = new HealthUtil();

	public double getPremiumAmount(PremiumForm form) {
		double amountToBePaid = 0;
		// condition based on age
		amountToBePaid = totalPercentByAgeLimit(form.getAge());
		// condition based on gender
		amountToBePaid = amountByGender(form.getGender(), amountToBePaid);
		// condition based on Health and Habits
		amountToBePaid = getCostForHealthStatus(form, amountToBePaid);
		return amountToBePaid;
	}

	private double totalPercentByAgeLimit(int age) {
		double amount = 0;
		amount = age <= 40 ? util.calculatePoint(age) : util.calculatePoint(age) + (util.calculatePoint(age) * 0.2);
		return amount;
	}

	private double amountByGender(String gender, double amount) {
		double amountByAge = 0;
		if (gender.equalsIgnoreCase(Constants.MALE) || gender.equalsIgnoreCase(Constants.FEMALE)) {
			amountByAge = amount + (amount * 0.02);
		}
		return amountByAge;
	}

	private double getCostForHealthStatus(PremiumForm form, double amount) {
		if (form.getBloodPressure().equalsIgnoreCase(Constants.YES)) {
			amount = amount + (amount * 0.01);
		}
		if (form.getBloodSugar().equalsIgnoreCase(Constants.YES)) {
			amount = amount + (amount * 0.01);
		}
		if (form.getHypertension().equalsIgnoreCase(Constants.YES)) {
			amount = amount + (amount * 0.01);
		}
		if (form.getOverWeight().equalsIgnoreCase(Constants.YES)) {
			amount = amount + (amount * 0.01);
		}
		if (form.getAlcohol().equalsIgnoreCase(Constants.YES)) {
			amount = amount + (amount * 0.03);
		}
		if (form.getDrugs().equalsIgnoreCase(Constants.YES)) {
			amount = amount + (amount * 0.03);
		}
		if (form.getSmoking().equalsIgnoreCase(Constants.YES)) {
			amount = amount + (amount * 0.03);
		}
		if (form.getDailyExercise().equalsIgnoreCase(Constants.YES)) {
			amount = amount - (amount * 0.03);
		}
		return amount;
	}
}

public class Main {
	public static void main(String[] args) {
		CalculatePremiumService calculatePremiumService = new CalculatePremiumService();
		PremiumForm form = new PremiumForm("diksha", "male", 20, "yes", "no", "no", "no", "no", "no", "no", "no");
		System.out.println(calculatePremiumService.getPremiumAmount(form));
	}
}
