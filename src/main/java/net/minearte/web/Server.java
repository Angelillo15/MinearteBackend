package net.minearte.web;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.javalin.Javalin;
import lombok.Getter;
import net.minearte.web.config.Config;
import net.minearte.web.config.ConfigLoader;
import net.minearte.web.utils.TebexUtils;

public class Server {
    @Getter
    private static Javalin app;
    public static void main(String[] args) {
        new ConfigLoader();
        ConfigLoader.loadConfig();
        app = Javalin.create()
                .get("/", ctx -> ctx.result("Online!"))
                .start(Config.port());

        app.get("/v1/store/checkout/{username}/{packageId}", ctx -> {
            String username = ctx.pathParam("username");
            String packageId = ctx.pathParam("packageId");
            JsonObject json = new JsonObject();
            json.addProperty("link", TebexUtils.getTebexLink(username, packageId));
            ctx.result(json.toString());
        });

        app.post("/v1/store/checkout/", ctx -> {
            System.out.println(ctx.body());
            JsonObject req = new JsonParser().parse(ctx.body()).getAsJsonObject();

            String username = req.get("username").getAsString();
            String packageId = req.get("packageId").getAsString();
            JsonObject response = new JsonObject();
            response.addProperty("link", TebexUtils.getTebexLink(username, packageId));
            ctx.result(response.toString());
        });

        app.get("/v1/store/packages", ctx -> {
            ctx.result(TebexUtils.getPackages());
        });

        setupScheduler();
    }

    public static void setupScheduler() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000 * 60 * 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TebexUtils.cleanCache();
                System.out.println("Cache cleaned!");
            }
        }).start();
    }
}
