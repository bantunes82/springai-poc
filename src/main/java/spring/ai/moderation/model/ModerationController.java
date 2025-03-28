package spring.ai.moderation.model;

import org.springframework.ai.moderation.Categories;
import org.springframework.ai.moderation.CategoryScores;
import org.springframework.ai.moderation.Moderation;
import org.springframework.ai.moderation.ModerationModel;
import org.springframework.ai.moderation.ModerationPrompt;
import org.springframework.ai.moderation.ModerationResponse;
import org.springframework.ai.moderation.ModerationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ModerationController {

    private final ModerationModel moderationModel;

    @Autowired
    public ModerationController(ModerationModel moderationModel) {
        this.moderationModel = moderationModel;
    }

    @GetMapping(value = "ai/moderation", produces = "application/json")
    public Map moderate(@RequestParam(value="message", defaultValue = "Text to be moderated") String message) {
        ModerationPrompt moderationPrompt = new ModerationPrompt(message);
        ModerationResponse moderationResponse = moderationModel.call(moderationPrompt);

        // Access the moderation results
        Moderation moderation = moderationResponse.getResult().getOutput();
        log(moderation);

        return Map.of("moderation", moderationResponse);
    }

    private static void log(Moderation moderation) {
        // Print general information
        System.out.println("Moderation ID: " + moderation.getId());
        System.out.println("Model used: " + moderation.getModel());

        // Access the moderation results (there's usually only one, but it's a list)
        for (ModerationResult result : moderation.getResults()) {
            System.out.println("\nModeration Result:");
            System.out.println("Flagged: " + result.isFlagged());

            // Access categories
            Categories categories = result.getCategories();
            System.out.println("\nCategories:");
            System.out.println("Sexual: " + categories.isSexual());
            System.out.println("Hate: " + categories.isHate());
            System.out.println("Harassment: " + categories.isHarassment());
            System.out.println("Self-Harm: " + categories.isSelfHarm());
            System.out.println("Sexual/Minors: " + categories.isSexualMinors());
            System.out.println("Hate/Threatening: " + categories.isHateThreatening());
            System.out.println("Violence/Graphic: " + categories.isViolenceGraphic());
            System.out.println("Self-Harm/Intent: " + categories.isSelfHarmIntent());
            System.out.println("Self-Harm/Instructions: " + categories.isSelfHarmInstructions());
            System.out.println("Harassment/Threatening: " + categories.isHarassmentThreatening());
            System.out.println("Violence: " + categories.isViolence());

            // Access category scores
            CategoryScores scores = result.getCategoryScores();
            System.out.println("\nCategory Scores:");
            System.out.println("Sexual: " + scores.getSexual());
            System.out.println("Hate: " + scores.getHate());
            System.out.println("Harassment: " + scores.getHarassment());
            System.out.println("Self-Harm: " + scores.getSelfHarm());
            System.out.println("Sexual/Minors: " + scores.getSexualMinors());
            System.out.println("Hate/Threatening: " + scores.getHateThreatening());
            System.out.println("Violence/Graphic: " + scores.getViolenceGraphic());
            System.out.println("Self-Harm/Intent: " + scores.getSelfHarmIntent());
            System.out.println("Self-Harm/Instructions: " + scores.getSelfHarmInstructions());
            System.out.println("Harassment/Threatening: " + scores.getHarassmentThreatening());
            System.out.println("Violence: " + scores.getViolence());
        }
    }
}
