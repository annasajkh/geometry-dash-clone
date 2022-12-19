package com.github.annasajkh.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.annasajkh.Game;
import com.github.annasajkh.components.Entity;
import com.github.annasajkh.objects.StaticObject;
import com.github.annasajkh.objects.StaticRectangle;
import com.github.annasajkh.utils.GameState;
import com.github.annasajkh.utils.Global;

public class Player extends Entity
{
    private Rectangle result;

    public Rectangle rectangle;
    public OrthographicCamera camera;

    private boolean isOnFloor = false;
    public boolean dead = false;

    public Player(Vector2 position)
    {
        super(position, Vector2.Zero, 50, 50, 0, Color.BLUE);

        rectangle = new Rectangle(position.x - width * 0.5f, position.y - height * 0.5f, width, height);
        result = new Rectangle();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // update the camera
        camera.position.x = position.x;
        camera.position.y = position.y;
        camera.update();

        velocity.x = 400;
    }

    public void handleCollision(Array<StaticObject> staticObjects)
    {
        out:
        for(StaticObject staticObject : staticObjects)
        {
            
            switch(staticObject.staticObjectType)
            {
                case STATIC_RECTANGLE:
                    StaticRectangle staticRectangle = (StaticRectangle)staticObject;
                    
                    boolean intersect = Intersector.intersectRectangles(rectangle, staticRectangle.rectangle, result);
                    
                    if(intersect)
                    {                        
                        //if it collide on the top then resolve it otherwise the player is dead
                        if(result.width > result.height && position.y > staticRectangle.position.y)
                        {
                            position.y += result.height;
                            velocity.y = 0;
                                                        
                            if(rotation % 90 != 0)
                            {
                                rotation -= rotation % 90;
                            }
                        }
                        
                        if(result.width + 5 > result.height && position.y > staticRectangle.position.y)
                        {   
                            isOnFloor = true;
                        }
                        else
                        {                                
                            dead = true;
                            isOnFloor = false;
                            break out;
                        }
                    }
                    break;
        
                case SPIKE:
                    boolean intersectPoint1 = Intersector.intersectSegmentRectangle(staticObject.position.x - width * 0.5f,
                                                                                    staticObject.position.y - height * 0.5f,
                                                                                    staticObject.position.x + width * 0.5f,
                                                                                    staticObject.position.y - height * 0.5f,
                                                                                    rectangle);
                    
                    boolean intersectPoint2 = Intersector.intersectSegmentRectangle(staticObject.position.x + width * 0.5f,
                                                                                    staticObject.position.y - height * 0.5f,
                                                                                    staticObject.position.x,
                                                                                    staticObject.position.y + height * 0.5f,
                                                                                    rectangle);
                    
                    boolean intersectPoint3 = Intersector.intersectSegmentRectangle(staticObject.position.x,
                                                                                    staticObject.position.y + height * 0.5f,
                                                                                    staticObject.position.x - width * 0.5f,
                                                                                    staticObject.position.y - height * 0.5f,
                                                                                    rectangle);
                    
                    if(intersectPoint1 || intersectPoint2 || intersectPoint3)
                    {
                        dead = true;
                        break out;
                    }
                    
                    break;
            }
        }
    }

    public void getInput()
    {
        if(Gdx.input.isKeyPressed(Keys.SPACE) && isOnFloor)
        {
            velocity.y = Global.jumpHeight;
            isOnFloor = false;
        }
    }

    public void finishUpdate()
    {
        if(position.y < -100)
        {
            dead = true;
        }

        if(velocity.y != 0)
        {
            rotation -= MathUtils.map(-Global.maxFallingSpeed, Global.maxFallingSpeed, 1, 3, velocity.y);
        }

        if(dead)
        {
            Game.reset();
            Game.gameState = GameState.EDIT;
        }

        if(velocity.y != 0)
        {
            isOnFloor = false;
        }
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);

        // update the rectangle collider
        rectangle.x = position.x - width * 0.5f;
        rectangle.y = position.y - height * 0.5f;
        rectangle.width = width;
        rectangle.height = height;

        // update the camera
        camera.position.x = position.x;
        camera.position.y = position.y;
        camera.update();
    }

    @Override
    public void render(ShapeRenderer shapeRenderer)
    {
        super.render(shapeRenderer);

        shapeRenderer.rect(position.x - width * 0.5f, position.y - height * 0.5f, width * 0.5f, height * 0.5f, width, height, 1, 1, rotation);
    }

}
