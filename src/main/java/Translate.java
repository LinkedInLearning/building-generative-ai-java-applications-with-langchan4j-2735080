import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Translate {
    public static void main(String[] argv) {
        List<ChatMessage> messages = new ArrayList<>();

        ChatModel cmodel = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .modelName(OpenAiChatModelName.GPT_4_O)
                .temperature(.2)                        // keep randomness low
                .timeout(Duration.ofSeconds(120))       // network timeout is 2 min
                .maxTokens(512)                         // keep data charges low
                .build();

        messages.add(UserMessage.from("Translate the following text to Italian"));

        String mytext = """
                Java is a high-level, object-oriented programming language designed to be platform-independent,
                meaning code written in Java can run on any device with a Java Virtual Machine (JVM).
                It was developed by Sun Microsystems (now owned by Oracle) and is widely used for
                building enterprise applications, web services, Android apps, and backend systems.
                Java emphasizes readability, maintainability, and security, and follows the
                principle of "write once, run anywhere," making it a popular choice for developers
                across industries.
                """;
        messages.add(UserMessage.from(mytext));

        var answer = cmodel.chat(messages);     // send to the LLM

        System.out.println("ENGLISH");
        System.out.println(mytext);                     // original text
        System.out.println("ITALIAN");
        System.out.println(answer.aiMessage().text());  // translation
    }
}
