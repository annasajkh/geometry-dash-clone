package com.github.annasajkh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.annasajkh.entities.Player;
import com.github.annasajkh.objects.Spike;
import com.github.annasajkh.objects.StaticObject;
import com.github.annasajkh.objects.StaticObject.StaticObjectType;
import com.github.annasajkh.objects.StaticRectangle;
import com.github.annasajkh.utils.GameState;
import com.github.annasajkh.utils.InputManager;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Game extends ApplicationAdapter
{
    public static ShapeRenderer shapeRenderer;
    public static Player player;
    public static Array<StaticObject> staticObjects;
    public static Spike spike;
    public static GameState gameState;
    public static StaticObjectType spawnType = StaticObjectType.STATIC_RECTANGLE;
    
    public static void init()
    {
        staticObjects = new Array<>();
        
        
        staticObjects.add(new StaticRectangle(new Vector2(200, 0),
                                              5000, 
                                              500, 
                                              0, 
                                              Color.WHITE));
        
        player = new Player(new Vector2(0, 400));
        
        gameState = GameState.EDIT;
        
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new InputManager());
        Gdx.input.setInputProcessor(inputMultiplexer);
    }
    
    @Override
    public void create()
    {
        shapeRenderer = new ShapeRenderer();
        
        init();
    }
    
    public static void reset()
    {
        player = new Player(new Vector2(0, 400));
    }
    
    public void getInput()
    {   
        player.getInput();
    }
    
    public void update(float delta)
    {
        getInput();
        player.update(delta);
        player.handleCollision(staticObjects);
        player.finishUpdate();
    }

    @Override
    public void render()
    {   
        switch(gameState)
        {
            case EDIT:
                
                break;
    
            case PLAY:
                    update(Gdx.graphics.getDeltaTime());
                break;
        }     
        
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        shapeRenderer.setProjectionMatrix(player.camera.combined);
        shapeRenderer.begin(ShapeType.Filled);
        
        player.render(shapeRenderer);
        
        for(StaticObject staticObject : staticObjects)
        {
            staticObject.render(shapeRenderer);
        }
        
        shapeRenderer.end();
    }

    @Override
    public void dispose()
    {
        shapeRenderer.dispose();
    }
}