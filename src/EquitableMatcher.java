import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationUtils;

public class EquitableMatcher {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("usage: EquitableMatcher <input>\n");
			return;
		}
		EquitableMatcher em = new EquitableMatcher();
		em.match(args[0]);
	}


	private void match(String filename) {

		List<List<Person>> groups = InputParserUtility.ParseInput(filename);
		List<List<Person>> manOptimalMatch = GaleShapelyAlgorithm.execute(cloneGroups(groups), "m");
		List<List<Person>> womanOptimalMatch = GaleShapelyAlgorithm.execute(cloneGroups(groups), "w");

		Long equityScore = calculateEquityScore(manOptimalMatch);
		System.out.println("Man-optimal equity score: " + equityScore);
		equityScore = calculateEquityScore(womanOptimalMatch);
		System.out.println("Woman-optimal equity score: " + equityScore);
	}


	private Long calculateEquityScore(List<List<Person>> manOptimalMatch) {
		List<Person> men = manOptimalMatch.get(0);
		List<Person> women = manOptimalMatch.get(1);
		Long equity = 0L;
		equity = men.stream() //
				.map(man -> calculatePreferenceScore(man, women))
				.reduce(0L, (a, b) -> a + b);
		equity += women.stream() //
				.map(woman -> calculatePreferenceScore(woman, men))
				.reduce(0L, (a, b) -> a + b);
		return equity;
	}


	private Long calculatePreferenceScore(Person person, List<Person> preferenceGroup) {
		Integer matchIndex = preferenceGroup.indexOf(person.getMatch());
		if (matchIndex < 0) {
			throw new RuntimeException("invalid match");
		}
		Integer preferenceListIndex = person.getPreferenceList().indexOf(matchIndex);
		return preferenceListIndex.longValue();
	}


	private List<List<Person>> cloneGroups(List<List<Person>> groups) {
		return groups.stream() //
				.map(personList -> personList.stream() //
						.map(person -> SerializationUtils.clone(person)) //
						.collect(Collectors.toList())) //
				.collect(Collectors.toList());
	}




}
