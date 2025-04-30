import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;

import java.util.Scanner;

public class ChatContext {
    public static void main(String[] args) {
        Scanner userinput;
        String cmdline;

        ChatLanguageModel cmodel = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .modelName(OpenAiChatModelName.GPT_4)
                .build();

        ChatMemory cm = MessageWindowChatMemory.withMaxMessages(100);

        SystemMessage sysmsg = new SystemMessage("""
                    You are a polite software consultant with deep expertise in teaching AI and Machine Learning.
                """);
        cm.add(sysmsg);


        while (true) {
            System.out.print("prompt> ");

            userinput = new Scanner(System.in);
            cmdline = userinput.nextLine();

            if (cmdline.isBlank())       // If nothing, do nothing
                continue;

            UserMessage usrmsg = UserMessage.from(cmdline);   // create the prompt
            cm.add(usrmsg);                                   // Add the user prompt to the ChatMemory

            var answer = cmodel.chat(cm.messages());  // send the context as messages and save the response
            var response = answer.aiMessage().text();

            System.out.println(response);

            cm.add(UserMessage.from(response));     // Add the response from the assistant
        }
    }
}
