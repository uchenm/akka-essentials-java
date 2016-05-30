package ch2.first.akka.app.messages;

public class WordCount {

	private String word;
	private Integer count;

	public WordCount(String inWord, Integer inCount) {
		word = inWord;
		count = inCount;
	}

	public String getWord() {
		return word;
	}

	public Integer getCount() {
		return count;
	}
}
