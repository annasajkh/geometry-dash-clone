package com.github.annasajkh.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject
{
    public Vector2 position;
    public float width, height;
    public float rotation;
    
    public Color color;
    
    public GameObject(Vector2 position, float width, float height, float rotation, Color color)
    {
        this.position = position;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.color = color;
    }
    
    public abstract void update(float delta);
    
    public void render(ShapeRenderer shapeRenderer)
    {
        shapeRenderer.setColor(color);
    }

}
