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

	}


	private List<List<Person>> cloneGroups(List<List<Person>> groups) {
		return groups.stream() //
				.map(personList -> personList.stream() //
						.map(person -> SerializationUtils.clone(person)) //
						.collect(Collectors.toList())) //
				.collect(Collectors.toList());
	}




}
