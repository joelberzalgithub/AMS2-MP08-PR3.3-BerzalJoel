package com.badlogic.comunicacions;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.Arrays;

public class Main extends ApplicationAdapter {
	Dialog dlg;
	Skin skin;
	Stage stage;
	TextButton btn;

	@Override
	public void create() {
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage();

		dlg = new Dialog("Crida HTTP Estandard", skin);
		dlg.text("Pressiona aquest botó per fer una crida a la pagina d'inici de Google Chrome (https://www.google.de)");
		btn = new TextButton("Pressiona'm", skin);
		dlg.button(btn);
		dlg.show(stage);

		btn.addListener(event -> {
			HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
			Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("https://www.google.de").build();
			Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
				@Override
				public void cancelled() {
					System.out.println("Crida cancel·lada");
				}

				@Override
				public void failed(Throwable t) {
					System.out.println("Crida fallida: " + t.getMessage());
				}

				@Override
				public void handleHttpResponse(Net.HttpResponse httpResponse) {
					System.out.println("Resposta rebuda: " + Arrays.toString(httpResponse.getResult()));
				}
			});
            return true;
        });
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
