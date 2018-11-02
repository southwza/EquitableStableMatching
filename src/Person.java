import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Person implements Serializable {

	private static final long serialVersionUID = 8934557391835532836L;
	private List<Integer> preferenceList = List.of();
	private Person match;
	private Person lastRejected;
	private int position;

	public Person(int position) {
		this.position = position;
	}

	public List<Integer> getPreferenceList() {
		return preferenceList;
	}

	public void setPreferenceList(List<Integer> preferenceList) {
		this.preferenceList = preferenceList;
	}

	public Person getMatch() {
		return match;
	}

	public void setMatch(Person match) {
		this.match = match;
	}

	public Person getLastRejected() {
		return lastRejected;
	}

	public void setLastRejected(Person lastRejected) {
		this.lastRejected = lastRejected;
	}

	public boolean prefers(Person personA, Person personB) {
		return preferenceList.indexOf(personA.position) < preferenceList.indexOf(personB.position);
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Person)) {
			return false;
		}
		Person person = (Person) o;
		return position == person.position;
	}

	@Override
	public int hashCode() {
		return Objects.hash(position);
	}
}
