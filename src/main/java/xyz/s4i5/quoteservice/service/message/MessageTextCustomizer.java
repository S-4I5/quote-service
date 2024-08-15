package xyz.s4i5.quoteservice.service.message;

import lombok.experimental.UtilityClass;
import xyz.s4i5.quoteservice.model.entity.VkUser;

import java.util.List;
import java.util.Random;

@UtilityClass
public class MessageTextCustomizer {
    private static final List<String> PREFIX_LIST = List.of("Однажды", "Когда-то", "Один чудесным вечером");
    private static final List<String> POSTFIX_LIST = List.of("промямлил", "встал, подошёл и уверенно сказал", "заявил");
    private static final Random random = new Random();

    public String customize(VkUser user, String message) {
        return String.format("%s %s %s %s : '%s'",
                PREFIX_LIST.get(random.nextInt(0, 2)),
                user.getFirstName(),
                user.getSecondName(),
                POSTFIX_LIST.get(random.nextInt(0, 2)),
                message
        );
    }
}
