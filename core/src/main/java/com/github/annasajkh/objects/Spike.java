package com.github.annasajkh.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Spike extends StaticObject
{   
    public Spike(Vector2 position, float width, float height, float rotation, Color color)
    {
        super(position, StaticObjectType.SPIKE, width, height, rotation, color);
        
        
    }

    @Override
    public void update(float delta)
    {
        
    }

    @Override
    public void render(ShapeRenderer shapeRenderer)
    {
        super.render(shapeRenderer);
        
        shapeRenderer.triangle(position.x - width * 0.5f, 
                               position.y - height * 0.5f,
                               position.x + width * 0.5f,
                               position.y - width * 0.5f,
                               position.x,
                               position.y + height * 0.5f);
        
    }

}
