package fr.unice.l3.allumettes.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import fr.unice.l3.allumettes.R;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Allumettes extends View {

    private Drawable allumette;
    private int nbAllumettesTotal = 21;
    private int nbAllumettesEnlevees = 0;
    private int nbAllumettesSelectionnees = 0;
    private int allumetteLargeur;
    private int allumetteHauteur;
    private int espaceEntreAllumettes;
    private int espaceBordEcran;
    private Paint selectionPaint;
    private Paint removedPaint;

    public Allumettes(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        allumette = context.getDrawable(R.drawable.allumette);

        selectionPaint = new Paint();
        selectionPaint.setColor(Color.GREEN);
        selectionPaint.setStyle(Paint.Style.STROKE);
        selectionPaint.setStrokeWidth(10);

        removedPaint = new Paint();
        removedPaint.setColor(Color.BLACK);
        removedPaint.setStyle(Paint.Style.STROKE);
        removedPaint.setStrokeWidth(5);
        removedPaint.setPathEffect(new DashPathEffect(new float[]{25, 25}, 0));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        espaceBordEcran = w / (nbAllumettesTotal * 2);
        espaceEntreAllumettes = espaceBordEcran;
        allumetteLargeur = (w - (espaceEntreAllumettes * (nbAllumettesTotal - 1)) - 2 * espaceBordEcran) / nbAllumettesTotal;
        allumetteHauteur = h * 2 / 3;

        // On adapte la largeur de l'image pour conserver les proportions
        allumette.setBounds(0, 0, allumette.getIntrinsicWidth() * allumetteHauteur / allumette.getIntrinsicHeight(), allumetteHauteur);
    }

    public void setSelectedCount(int nbAllumettesSelectionnees) {
        this.nbAllumettesSelectionnees = nbAllumettesSelectionnees;
        invalidate(); // Redessinez la vue avec les nouvelles allumettes sélectionnées
    }

    public void setRemovedCount(int nbAllumettesEnlevees) {
        this.nbAllumettesEnlevees = nbAllumettesEnlevees;
        invalidate(); // Redessinez la vue avec les nouvelles allumettes enlevées
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int totalCount = nbAllumettesTotal;
        int x = espaceBordEcran;
        int y = (getHeight() - allumetteHauteur) / 2;

        for (int i = 0; i < totalCount; i++) {
            if (i < nbAllumettesTotal - nbAllumettesEnlevees) {
                allumette.setBounds(x, y, x + allumetteLargeur, y + allumetteHauteur);
                allumette.draw(canvas);
            }

            if (i >= nbAllumettesTotal - nbAllumettesEnlevees - nbAllumettesSelectionnees && i < nbAllumettesTotal - nbAllumettesEnlevees) {
                Rect rect = new Rect(x - 5, y - 5, x + allumetteLargeur + 5, y + allumetteHauteur + 5);
                canvas.drawRect(rect, selectionPaint);
            } else if (i >= nbAllumettesTotal - nbAllumettesEnlevees) {
                Rect rect = new Rect(x - 5, y - 5, x + allumetteLargeur + 5, y + allumetteHauteur + 5);
                canvas.drawRect(rect, removedPaint);
            }

            x += allumetteLargeur + espaceEntreAllumettes;
        }
    }




    public void setVisibleCount(int nbAllumettesVisibles) {
        this.nbAllumettesEnlevees = nbAllumettesTotal - nbAllumettesVisibles - nbAllumettesSelectionnees;
        invalidate(); // Redessinez la vue avec les nouvelles allumettes visibles
    }

    public void setTotalCount(int totalCount) {
        this.nbAllumettesTotal = totalCount;
        invalidate(); // Redessinez la vue avec les nouvelles allumettes totales
    }


}
