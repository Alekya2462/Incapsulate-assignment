package com.incapsulate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DishesEachTimeOfDay {
	private static Map<String, String> morningDishes = null;
	private static Map<String, String> nightDishes = null;
	private static Map<String, Map<String, String>> dishesEachTimeOfDay = null;
	private static Map<String, List<String>> dishesOrderMultipleTimes = null;

	/*
	 * Below method will load all the morning and night dishes mapping in
	 * hashmap.
	 */
	public void loadDishes() {
		morningDishes = new HashMap<String, String>();
		nightDishes = new HashMap<String, String>();
		dishesEachTimeOfDay = new HashMap<String, Map<String, String>>();
		dishesOrderMultipleTimes = new HashMap<String, List<String>>();
		List<String> morningOrderMultipleDishes = new ArrayList<String>();
		List<String> nightOrderMultipleDishes = new ArrayList<String>();
		morningDishes.put("1", "eggs");
		morningDishes.put("2", "toast");
		morningDishes.put("3", "coffee");
		nightDishes.put("1", "steak");
		nightDishes.put("2", "potato");
		nightDishes.put("3", "wine");
		nightDishes.put("4", "cake");
		dishesEachTimeOfDay.put("morning", morningDishes);
		dishesEachTimeOfDay.put(“night”, nightDishes);
		morningOrderMultipleDishes.add("3");
		nightOrderMultipleDishes.add("2");
		dishesOrderMultipleTimes.put("morning", morningOrderMultipleDishes);
		dishesOrderMultipleTimes.put(“night”, nightOrderMultipleDishes);
	}

	public static void main(String args[]) {
		String[] inputArguments = args[0].split(",");
		//first parameter is timeOfDay.
		String timeOfDay = inputArguments[0].toLowerCase();
		//load all the dishes on timely manner using loadDishes()
		DishesEachTimeOfDay dishesEachTimeOfDay = new DishesEachTimeOfDay();
		dishesEachTimeOfDay.loadDishes();
		//get all the counts of dishes marked, for eg: if user entered 1 3 times put this part of hashmap with key as 1 and value as count 3.
		Map<String, Integer> dishesCount = new HashMap<String, Integer>();
		for (int i = 1; i < inputArguments.length; i++) {
			Integer count = dishesCount.get(inputArguments[i]) == null ? 0 : dishesCount.get(inputArguments[i]);
			if (count == 0) {
				dishesCount.put(inputArguments[i], 1);
			} else {
				dishesCount.put(inputArguments[i], ++count);
			}
		}
		//fetch all the dishes by calling fetchDishes.
		dishesEachTimeOfDay.fetchDishes(timeOfDay, dishesCount);
	}

	public void fetchDishes(String timeOfDay, Map<String, Integer> dishes) {
		Map<String, String> morningDishes = dishesEachTimeOfDay.get(timeOfDay);
		List<String> multipleTimes = dishesOrderMultipleTimes.get(timeOfDay);
		String output = "";
		String error = "";
		boolean checkMultipleTimes = true;
		//iterate through the input dishes arguments.
		for (Map.Entry<String, Integer> entry : dishes.entrySet()) {
			//Check whether the dishes available for the provided arguments.
			if (morningDishes.containsKey(entry.getKey())) {
				//Check how many times the dish is ordered, for eg: if user entered 3(coffee) 3 times then provide it as Coffee(x3).
				String count = entry.getValue() == 1 ? "" : "(x" + entry.getValue() + ")";
				if (entry.getValue() > 1) {
					checkMultipleTimes = multipleTimes.contains(entry.getKey());
				}
				if (checkMultipleTimes) {
					output = output + "," + morningDishes.get(entry.getKey()) + count;
				} else {
					output = output + "," + morningDishes.get(entry.getKey()) + ",ERROR";
					break;
				}
			//if there is no value present, then provide the ERROR.	
			} else {
				error = "The provided input:" + entry.getKey()
						+ "is not available. Please select the items from the below menu.";
				error = error + "\n" + morningDishes;
				output = output + "," + "ERROR";
				break;
			}

		}
		System.out.println(output.substring(1));
		System.out.println(error);
	}
}
