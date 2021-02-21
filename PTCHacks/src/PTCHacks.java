import javax.swing.*;
import java.io.*;

public class PTCHacks {
	public static void main(String[] args) {
		String[] physicalConditionsPrompts = { "I have an autoimmune condition.", "I am diabetic.",
				"I have dementia.", "I had a stroke.", "I have cancer.",
				"I underwent chemotherapy.", "I have another chronic illness.", "I am pregnant." };
		String[] physicalConditions = { "overweight", "over 60 years old", "autoimmune condition", "diabetes",
				 "dementia", "stroke", "cancer", "chemotherapy", "other chronic illness",
				"pregnant"};
		boolean[] patientPhysicalConditions = new boolean[physicalConditions.length];

		
		String[] socialConditionsPrompts = {"I am First Nations, Métis, or Inuit.", "I work in healthcare.", "I work or live in senior long-term care.", 
				"I am receiving chronic home care.", "I work or live in other congregate living (shelter)", "I am a frontline worker, \nincluding first response, teachers, and foodprocessing.", "I care for someone with a chronic illness"};
		String[] socialConditions = {"indigenous", "working in healthcare", "working/resident in  senior long-term care", "receiving chronic home care",
		"working/resident in congregate living", "a frontline worker", "caring for someone with chronic illness"};
		
		boolean[] patientSocialConditions = new boolean[socialConditions.length];
		
		int tryAgain, saveFile;
		
		double[] basicInfo = new double[3];
		
		ImageIcon welcome = new ImageIcon("VaccinateMeWelcome.png");
		ImageIcon logo = new ImageIcon("logo.png");
		ImageIcon goodbye = new ImageIcon("goodbye.png");
		
		
		//welcome picture
		JOptionPane.showMessageDialog(null, "", "VaccinateMe",
				JOptionPane.PLAIN_MESSAGE, welcome);
		
		do { 
			//basic demographics (age/ height/ weight) for BMI and check age risk level
			boolean adult = basicInfoCheck(patientPhysicalConditions, basicInfo);
			
			//more nuanced physical  + social factors in phase decision
			checkConditions(physicalConditionsPrompts, patientPhysicalConditions);
			JOptionPane.showMessageDialog(null, "Now we will check for social factors...", "Social Factors", JOptionPane.PLAIN_MESSAGE, logo);
		
			checkSocialConditions(socialConditionsPrompts, patientSocialConditions);
			
			
			// determining which phase they are eligible for ... only one will print
			if (phaseOne(patientSocialConditions, adult)) {}
			else if (phaseTwo(patientSocialConditions, patientPhysicalConditions)) {}	
			else {
				phaseThree();
			}
			
			//vaccine PHYSICAL risk level if you were to get infected
			riskCheck(patientPhysicalConditions, physicalConditions);
			
			
			// file saver
			saveFile = JOptionPane.showConfirmDialog(null, "Do you want to save your file and send it to Public Health?", "Save as File",
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, logo);
			
			if (saveFile == 0) {
				fileWrite(basicInfo, patientPhysicalConditions, physicalConditions, patientSocialConditions, socialConditions);
			}
			
			// note for healthcare worker
			if (patientSocialConditions[1]) {
				healthcareMessage(logo);
			}
		
			// note for pregnant women
			if (patientPhysicalConditions[9]) {
				pregnantMessage(logo);
			}
			
			
			// note for before you get vaccinated
			vaccineMessage();
			
			tryAgain = JOptionPane.showConfirmDialog(null, "Do you want to check another person?", "Continue?",
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, logo);
			
		} // end of d
		while (tryAgain == 0);
		JOptionPane.showMessageDialog(null, "", "VaccinateMe",
				JOptionPane.PLAIN_MESSAGE, goodbye);
	}
	
	
	public static boolean basicInfoCheck(boolean[] patientConditions, double[] basicInfo) {
		double age=-1, height=-1, weight=-1;
		boolean exception = true;

		while (exception) {
			try {
				age = Double.parseDouble(getString("age", "years"));
				exception = false;
			} catch (NumberFormatException nf) {
				JOptionPane.showMessageDialog(null, "You entered invalid input, please enter a number.");
				age = Double.parseDouble(getString("age", "years"));
			} catch (NullPointerException np) {
				JOptionPane.showMessageDialog(null, "You entered invalid input, please enter a number.");
				age = Double.parseDouble(getString("age", "years"));
			}
		}
		
		basicInfo[0] = age;
		if (age >= 60) {
			patientConditions[1] = true;
		}
		
		exception = true;
		while (exception) {
			try {
				height = Double.parseDouble(getString("height", "metres"));
				exception = false;
			} catch (NumberFormatException nf) {
				JOptionPane.showMessageDialog(null, "You entered invalid input, please enter a number.");
				height = Double.parseDouble(getString("height", "metres"));
			} catch (NullPointerException np) {
				JOptionPane.showMessageDialog(null, "You entered invalid input, please enter a number.");
				height = Double.parseDouble(getString("height", "metres"));
			}
		}
		basicInfo[1] = height;
		
		exception = true;
		while (exception) {
			try {
				weight = Double.parseDouble(getString("weight", "kilograms"));
				exception = false;
			} catch (NumberFormatException nf) {
				JOptionPane.showMessageDialog(null, "You entered invalid input, please enter a number.");
				weight = Double.parseDouble(getString("weight", "kilograms"));
			} catch (NullPointerException np) {
				JOptionPane.showMessageDialog(null, "You entered invalid input, please enter a number.");
				weight = Double.parseDouble(getString("weight", "kilograms"));
			}
		}
		basicInfo[2] = weight;
		
		double bmi = weight / (height * height);
		
		if (bmi > 25) {
			patientConditions[0] = true;
		}
		
		if (age >= 18) {return true;}
		else {return false;}
		
		
	}
	
	public static String getString(String prompt, String type) {
		String strInput;
		ImageIcon logo = new ImageIcon("logo.png");
		
		strInput = (String) JOptionPane.showInputDialog(null, "Enter your " + prompt + " in " + type, prompt, JOptionPane.PLAIN_MESSAGE, logo, null, null);
		
		while (strInput == null || strInput.equals("")) {
			JOptionPane.showMessageDialog(null, "You didn't enter any input");
			strInput = (String) JOptionPane.showInputDialog(null, "Enter your " + prompt + " in " + type, prompt, JOptionPane.PLAIN_MESSAGE, logo, null, null);
			
		}
		
		return strInput;
	}
	
	public static void  fileWrite(double[] basicInfo, boolean[] patientPhysicalConditions, String[] physicalConditions, boolean[] patientSocialConditions, String[] socialConditions) {
		try {
			ImageIcon logo = new ImageIcon("logo.png");
			String output = "";
			String name = getString("name", "full");
			output += "Name: " + name +"\nAge: " + basicInfo[0]+ "\nHeight: " + basicInfo[1] + "\nWeight: " + basicInfo[2] +"\n";
			
			PrintWriter pw = new PrintWriter(new FileWriter(name + ".txt"));
			for (int i = 0; i < patientPhysicalConditions.length; i++) {
				output+= "\n" + physicalConditions[i] + ": " + patientPhysicalConditions[i];
			}
			
			for (int i = 0; i < patientSocialConditions.length; i++) {
				output+= "\n" + socialConditions[i] + ": " + patientSocialConditions[i];
			}
			
			
			pw.print(output);
			
			pw.println("\n\n~data is user entered into VaccinateMe program");
			pw.close();
			
			JOptionPane.showMessageDialog(null, "File is saved as .txt on your computer!", "Saved!", JOptionPane.PLAIN_MESSAGE, logo);
		}		
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void checkConditions(String[] conditionsPrompts, boolean[] patientConditions) {
		int checking, display;

		ImageIcon[] icons = { new ImageIcon("autoimmune.jpg"), new ImageIcon("diabetes.jpg"), new ImageIcon("dementia.png"), new ImageIcon("stroke.png"),
				new ImageIcon("cancer.jpg"), new ImageIcon("chemo.jpg"), new ImageIcon("chronic.png"),
				new ImageIcon("pregnant.jpg") };

		for (int condition = 0; condition < conditionsPrompts.length; condition++) {
			display = condition+1;
			checking = (int) JOptionPane.showConfirmDialog(null,
					"Do you agree with this statement?\n" + conditionsPrompts[condition],
					"Checking Physical Condition " + display + "/" + conditionsPrompts.length, JOptionPane.YES_NO_OPTION,
					JOptionPane.PLAIN_MESSAGE, icons[condition]);
			if (checking == 0) {
				patientConditions[condition + 2] = true;
			} else {
				patientConditions[condition + 2] = false;
			}
		}
	}

	public static void checkSocialConditions(String[] socialConditionsPrompts, boolean[] patientSocialConditions) {
		int checking, display;

		ImageIcon[] icons = { new ImageIcon("indigenous.png"), new ImageIcon("healthcare.png"),
				new ImageIcon("seniorlongterm.png"), new ImageIcon("chronichealthcare.png"), new ImageIcon("congregateliving.png"),
				new ImageIcon("frontlineworker.png"), new ImageIcon("caregiver.png")};

		for (int condition = 0; condition < socialConditionsPrompts.length; condition++) {
			display = condition +1;
			checking = (int) JOptionPane.showConfirmDialog(null,
					"Do you agree with this statement?\n" + socialConditionsPrompts[condition],
					"Checking Social Condition " + display + "/" + socialConditionsPrompts.length, JOptionPane.YES_NO_OPTION,
					JOptionPane.PLAIN_MESSAGE, icons[condition]);
			if (checking == 0) {
				patientSocialConditions[condition] = true;
			} else {
				patientSocialConditions[condition] = false;
			}
		}
	}
	
	public static boolean phaseOne(boolean[]patientSocialConditions, boolean adult) {
		JTextArea area = new JTextArea();
		boolean phase = false;
		String output = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
				+ "It is estimated you can get the vaccine RIGHT NOW. Phase one began\n "
				+ "in December 2020 and is exclusive, meaning your employer or doctor\n"
				+"should have contacted you to get vaccinated\n"
				+ "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
				+"You are eligible for PHASE ONE because:\n";
		
		if  (patientSocialConditions[0] && adult) {
			phase = true;
			output += "- You are an Indigenous Adult\n";
		}
		
		if (patientSocialConditions[2]) {
			phase = true;
			output += "- You live or work in a Senior long-term care facility\n";
		}
		if (patientSocialConditions[3] && adult) {
			phase = true;
			output += "- You are an Adult receiving chronic home health care\n";
		}
		
		if (patientSocialConditions[1]) {
			phase = true;
			output += "- You are a healthcare worker\n\n";
			output += "Note:\n- Workers in Hospitals and LTC are prioritized for vaccination";
		}
		
		if (phase) {
		area.setText(output);

		JOptionPane.showMessageDialog(null, area, "PHASE ONE ELIGIBLE", JOptionPane.PLAIN_MESSAGE);
		}
		return phase;
	}

	public static boolean phaseTwo(boolean[] patientSocialConditions, boolean[] patientPhysicalConditions){
		JTextArea area = new JTextArea();
		boolean phase = false;
	
		String output = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
				+ "It is estimated you can get the vaccine during PHASE TWO. It will\n "
				+ "begin in March to July 2021, depending on availability\n"
				+"more information will be provided closer to the release\n"
				+ "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
				+"You are eligible for PHASE TWO because:\n";
		String notes = "";
		
		if (patientSocialConditions[4]) {
			phase = true;
			output += "- You live or work in congregate living\n";
		}
		if (patientSocialConditions[5]) {
			phase = true;
			output += "- You are a frontline worker\n";
		}
		if (patientSocialConditions[6]) {
			phase = true;
			output += "- You care for someone with a chronic illness\n";
		}
		
		if (patientPhysicalConditions[1]) {
			phase = true;
			output += "- You are over 60 years old\n";
			notes += "Note:\n- 80 and over are vaccinated first";
		}
		if (patientPhysicalConditions[2]) {
			phase = true;
			output += "- You have an autoimmune condition\n";
		}
		if (patientPhysicalConditions[3]) {
			phase = true;
			output += "- You are diabetic\n";
		}
		if (patientPhysicalConditions[4]) {
			phase = true;
			output += "- You have dementia\n";
		}
		
		if (patientPhysicalConditions[5]) {
			phase = true;
			output += "- You had a stroke\n";
		}
		if (patientPhysicalConditions[6]) {
			phase = true;
			output += "- You have cancer\n";
		}
		if (patientPhysicalConditions[7]) {
			phase = true;
			output += "- You underwent chemotherapy recently\n";
		}
		if (patientPhysicalConditions[8]) {
			phase = true;
			output += "- You have a high risk chronic illness\n";
		}
	
		if (phase) {
		output += notes;
		area.setText(output);
		JOptionPane.showMessageDialog(null, area, "PHASE TWO ELIGIBLE", JOptionPane.PLAIN_MESSAGE);
		}
		return phase;
	}

	public static void phaseThree() {
		JTextArea area = new JTextArea();
	
		String output = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
				+ "It is estimated you can get the vaccine during PHASE THREE. It \n "
				+ "begins in August 2021 and beyond, depending on availability.\n"
				+"more information will be provided closer to the release\n"
				+ "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
				+"You are eligible for PHASE THREE because:\n"
				+"- You are NOT considered at risk (this is the final Phase):\n";
		area.setText(output);
		JOptionPane.showMessageDialog(null, area, "PHASE TWO ELIGIBLE", JOptionPane.PLAIN_MESSAGE);
	}

	public static void riskCheck(boolean[] patientPhysicalConditions, String[] factors) {
		int totalRisk = 0;
		JTextArea area = new JTextArea();
		
		String stuff = "";
		
		for (int i = 0; i < patientPhysicalConditions.length; i++) {
			if (patientPhysicalConditions[i]) {
				totalRisk += 1;
				
				stuff += "- " + factors[i] + "\n";
			}
		}
		
		String output = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
				+ "You meet " + totalRisk + " out of 10 physical COVID-19\n" +
				"physical risk factors. Note: regardless of risk level,\n" +
				"it is recommended to take the vaccine when eligible.\n"
				+ "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
				+"Your at-risk factors are:\n";
		
		area.setText(output+stuff);
			
		JOptionPane.showMessageDialog(null, area, "Risk Factors",
					JOptionPane.WARNING_MESSAGE);	
				
	}
	
	public static void healthcareMessage(ImageIcon logo) {
		JOptionPane.showMessageDialog(null, "If you are a: \n"
				+ " - nurse practitioner, registered nurse or registered practical nurse\n"
				+ " -  pharmacist, pharmacy student, intern or pharmacy technician\n"
				+ "\nIf you would like to, you can volunteer your skills to help \n"
				+"administer  COVID-19 vaccines and assist the province rollout.\n\n"
				+"Register through the government of Ontario Matching portal."
				+ "", "Healthcare Worker",
				JOptionPane.PLAIN_MESSAGE, logo);
	}
	
	public static void pregnantMessage(ImageIcon logo) {
		JOptionPane.showMessageDialog(null, "We understand that pregnancy is difficult:\n"
				+ "it is important to know that pregnant women are at higher risk of \n"
				+ "severe COVID infections, including higher rates of ICU stays.\n"
				+ "\nThough vaccines were not tested on pregnant women, it is still \n"
				+"recommended by the American College of Obstetricians and Gynecologists\n"
				+"and the Society for Maternal-Fetal Medicine. Studies are currently \n"
				+ "underway to study the effects more closesly.", "Pregnant Mother",
				JOptionPane.PLAIN_MESSAGE, logo);
	}
	
	public static void vaccineMessage() {
		ImageIcon vaccine = new ImageIcon("vaccine.jpg");
		String output = "Some quick tips before you get vaccinated:"
				+"\n - PLAN AHEAD: take the day off"
				+"\n - UNDERSTAND: find out the side effects of the different brands"
				+"\n - BE READY: you will need a second dose of Pfizer and Moderna"
				+"\n\nYOU MUST STILL FOLLOW ALL SOCIAL DISTANCING AND MASK WEARING POLICIES!";
		
		
		JOptionPane.showMessageDialog(null, output, "VaccinateMe Tips",
				JOptionPane.PLAIN_MESSAGE, vaccine);
		
	}
}
