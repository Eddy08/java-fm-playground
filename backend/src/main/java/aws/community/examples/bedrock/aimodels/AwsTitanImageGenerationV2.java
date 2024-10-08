package aws.community.examples.bedrock.aimodels;

import org.json.JSONObject;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;

/*
public class AwsTitanImageGenerationV2 {

        public static final List<String> STYLES = Arrays.asList(
                        "3d-model",
                        "analog-film",
                        "anime",
                        "cinematic",
                        "comic-book",
                        "digital-art",
                        "enhance",
                        "fantasy-art",
                        "isometric",
                        "line-art",
                        "low-poly",
                        "modeling-compound",
                        "neon-punk",
                        "origami",
                        "photographic",
                        "pixel-art",
                        "tile-texture");

        public static Response invoke(BedrockRuntimeClient client, String prompt, String style) {

                JSONArray promptsJson = new JSONArray(List.of(new JSONObject().put("text", prompt)));
                JSONObject jsonBody = new JSONObject()
                                .put("textToImageParams", promptsJson)
                                .put("cfg_scale", 20)
                                .put("steps", 100);
                if (STYLES.contains(style)) {
                        jsonBody.put("style_preset", style);
                }

                SdkBytes sdkBytesBody = SdkBytes.fromUtf8String(jsonBody.toString());

                InvokeModelRequest request = InvokeModelRequest.builder()
                                .modelId("amazon.titan-image-generator-v2:0")
                                .body(sdkBytesBody).build();

                InvokeModelResponse response = client.invokeModel(request);
                String imageBytes = new JSONObject(response.body().asUtf8String())
                                .getJSONArray("artifacts")
                                .getJSONObject(0)
                                .get("base64")
                                .toString();

                return new Response(imageBytes);
        }

        public record Request(String prompt, String style) {
        }

        public record Response(String imageByteArray) {
        }
}
 */
public class AwsTitanImageGenerationV2 {
        public static Response invoke(BedrockRuntimeClient client, String prompt) {
                JSONObject jsonBody = new JSONObject()
                                .put("taskType", "TEXT_IMAGE")
                                .put("imageGenerationConfig", new JSONObject()
                                                .put("cfgScale", 8)
                                                .put("seed", 0)
                                                .put("width", 1024)
                                                .put("height", 1024)
                                                .put("numberOfImages", 3))
                                .put("textToImageParams", new JSONObject()
                                                .put("text", prompt));

                SdkBytes sdkBytesBody = SdkBytes.fromUtf8String(jsonBody.toString());

                InvokeModelRequest invokeModelRequest = InvokeModelRequest.builder()
                                .modelId("amazon.titan-image-generator-v2:0")
                                .body(sdkBytesBody)
                                .build();

                InvokeModelResponse response = client.invokeModel(invokeModelRequest);

                String imageBytes = new JSONObject(response.body().asUtf8String())
                                .getJSONArray("artifacts")
                                .getJSONObject(0)
                                .get("base64")
                                .toString();

                return new Response(imageBytes);
        }

        public record Request(String prompt) {
        }

        public record Response(String imageByteArray) {
        }
}