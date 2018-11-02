import java.util.LinkedList;
import java.util.List;

public class GaleShapelyAlgorithm {

	public static List<List<Person>> execute(List<List<Person>> groups, String gender) {
		if (gender.equalsIgnoreCase("m")) {
			executeGSAlgorithm(groups, true);
			printOutput(groups, true);
		} else {
			executeGSAlgorithm(groups, false);
			printOutput(groups, false);
		}
		return groups;
	}

	private static void printOutput(List<List<Person>> matchedPeople, boolean menFirst) {
		List<Person> men = matchedPeople.get(0);
		List<Person> women = matchedPeople.get(1);
		for (int i = 0; i < men.size(); i++) {
			int matchIndex;
			if (menFirst) {
				Person match = men.get(i).getMatch();
				matchIndex = women.indexOf(match);
			} else {
				Person match = women.get(i).getMatch();
				matchIndex = men.indexOf(match);
			}
			System.out.println("(" + (i + 1) + ", " + (matchIndex + 1) + ")");
		}
	}

	private static void executeGSAlgorithm(List<List<Person>> groups, boolean manOptimal) {
		List<Person> proposingGroup;
		List<Person> receivingGroup;
		if (manOptimal) {
			proposingGroup = groups.get(0);
			receivingGroup = groups.get(1);
		} else {
			proposingGroup = groups.get(1);
			receivingGroup = groups.get(0);
		}

		LinkedList<Person> queue = new LinkedList<Person>(proposingGroup);
		while (!queue.isEmpty()) {
			Person proposer = queue.remove();
			Person acceptor = proposalRound(proposer, receivingGroup);
			if (acceptor.getMatch() != null) {
				queue.add(acceptor.getMatch());
			}
			acceptor.setMatch(proposer);
		}
	}

	private static Person proposalRound(Person proposer, List<Person> receivingGroup) {
		int preferenceListIndex = 0;

		if (proposer.getLastRejected() != null) {
			preferenceListIndex = proposer.getPreferenceList().indexOf(proposer.getLastRejected().getPosition()) + 1;
		}

		Integer possibleMatchIndex = proposer.getPreferenceList().get(preferenceListIndex);
		Person possibleMatch = receivingGroup.get(possibleMatchIndex);
		while (proposer.getMatch() == null) {
			if (propose(proposer, possibleMatch)) {
				proposer.setMatch(possibleMatch);
				return possibleMatch;
			} else {
				preferenceListIndex++;
				possibleMatchIndex = proposer.getPreferenceList().get(preferenceListIndex);
				possibleMatch = receivingGroup.get(possibleMatchIndex);
			}
		}
		//should never get here
		return null;
	}

	private static boolean propose(Person proposer, Person possibleMatch) {
		Person existingEngagement = possibleMatch.getMatch();
		if (existingEngagement == null) {
			return true;
		} else if (possibleMatch.prefers(proposer, existingEngagement)) {
			existingEngagement.setLastRejected(possibleMatch);
			existingEngagement.setMatch(null);
			return true;
		}
		return false;
	}
}
