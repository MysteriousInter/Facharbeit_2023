package de.korittky.spielplangenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpielplangeneratorApplicationTests {

	@Test
	void contextLoads() {
	}

	void toDoTest(){

		Verein[] vereine={new Verein("Beispielverein1",2),new Verein("Beispielverein2",45),new Verein("Beispielverein3",4)};
		Spielfest test= new Spielfest(8,4,"12.2.24","lessenich","12:00",
				vereine		);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		System.out.println(gson.toJson(test));

	}
}
