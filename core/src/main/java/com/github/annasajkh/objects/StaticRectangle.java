package com.github.annasajkh.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class StaticRectangle extends StaticObject
{
    public Rectangle rectangle;
    
    public StaticRectangle(Vector2 position, float width, float height, float rotation, Color color)
    {
        super(position, StaticObjectType.STATIC_RECTANGLE, width, height, rotation, color);
        
        rectangle = new Rectangle(position.x - width * 0.5f, position.y - height * 0.5f, width, height);
    }

    @Override
    public void update(float delta)
    {
        //update the rectangle collider
        rectangle.x = position.x - width * 0.5f;
        rectangle.y = position.y - height * 0.5f;
        rectangle.width = width;
        rectangle.height = height;
    }

    @Override
    public void render(ShapeRenderer shapeRenderer)
    {
        super.render(shapeRenderer);

        shapeRenderer.rect(position.x - width * 0.5f, position.y - height * 0.5f, width * 0.5f, height * 0.5f, width, height, 1, 1, rotation);
    }

}
