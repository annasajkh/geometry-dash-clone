package com.github.annasajkh.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.annasajkh.Game;
import com.github.annasajkh.objects.Spike;
import com.github.annasajkh.objects.StaticObject;
import com.github.annasajkh.objects.StaticObject.StaticObjectType;
import com.github.annasajkh.objects.StaticRectangle;

public class InputManager implements InputProcessor
{
    float touchX;
    float touchY;
    
    @Override
    public boolean keyDown(int keycode)
    {
        if(keycode == Keys.NUM_1)
        {
            Game.spawnType = StaticObjectType.STATIC_RECTANGLE;
        }
        
        if(keycode == Keys.NUM_2)
        {
            Game.spawnType = StaticObjectType.SPIKE;
        }
        
        if(keycode == Keys.SPACE && Game.gameState == GameState.EDIT)
        {
            Game.gameState = GameState.PLAY;
            Game.reset();
        }
        
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {   
        
        if(Gdx.input.isButtonPressed(1) && Game.gameState == GameState.EDIT)
        {           
            Vector3 pos = Game.player.camera.unproject(new Vector3(screenX, screenY, 0));
            
            touchX = pos.x;
            touchY = pos.y;            
        }
        
        if(Gdx.input.isButtonPressed(0) && Game.gameState == GameState.EDIT)
        {
            Vector3 pos = Game.player.camera.unproject(new Vector3(screenX, screenY, 0));
  
            for(int i = Game.staticObjects.size - 1; i > 0; i--)
            {
                StaticObject staticObject = Game.staticObjects.get(i);
        
                Rectangle rectangle = new Rectangle(staticObject.position.x - staticObject.width * 0.5f,
                                                    staticObject.position.y - staticObject.height * 0.5f,
                                                    staticObject.width,
                                                    staticObject.height);
                
                if(rectangle.contains(pos.x, pos.y))
                {
                    Game.staticObjects.removeIndex(i);
                    return false;
                }
            }
            
            Vector2 placePosition = new Vector2(Math.round(pos.x / 50) * 50, 
                                                Math.round(pos.y / 50) * 50);
            
           switch(Game.spawnType)
            {
                case STATIC_RECTANGLE:
                    Game.staticObjects.add(new StaticRectangle(placePosition, 50, 50, 0, Color.BLACK));                    
                    break;
        
                case SPIKE:
                    Game.staticObjects.add(new Spike(placePosition, 50, 50, 0, Color.RED));                    
                    break;
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        
        
        if(Gdx.input.isButtonPressed(1) && Game.gameState == GameState.EDIT)
        {
            Vector3 pos = Game.player.camera.unproject(new Vector3(screenX, screenY, 0));
            
            Game.player.camera.position.add(touchX - pos.x,touchY - pos.y,0);
            
            Game.player.camera.update();
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY)
    {
        if(Game.gameState == GameState.EDIT)
        {            
            if(amountY < 0)
            {
                Game.player.camera.zoom -= Global.zoomSpeed * Game.player.camera.zoom * Global.zoomFactor;
            }
            else
            {
                Game.player.camera.zoom += Global.zoomSpeed * Game.player.camera.zoom * Global.zoomFactor;
            }
            
            Game.player.camera.zoom = MathUtils.clamp(Game.player.camera.zoom, 0.0001f, Global.maxZoom);
            Game.player.camera.update();
            
        }
        return true;
    }

}
