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
    private int nbAllumettesVisibles = 21;
    private int allumetteLargeur;
    private int allumetteHauteur;
    private int espaceEntreAllumettes;
    private int espaceBordEcran;
    private int nbAllumettesSelectionnees = 0;
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

        espaceBordEcran = w / (nbAllumettesVisibles * 2);
        espaceEntreAllumettes = espaceBordEcran;
        allumetteLargeur = (w - (espaceEntreAllumettes * (nbAllumettesVisibles - 1)) - 2 * espaceBordEcran) / nbAllumettesVisibles;
        allumetteHauteur = h * 2 / 3;

        // On adapte la largeur de l'image pour conserver les proportions
        allumette.setBounds(0, 0, allumette.getIntrinsicWidth() * allumetteHauteur / allumette.getIntrinsicHeight(), allumetteHauteur);
    }

    public void setSelectedCount(int nbAllumettesSelectionnees) {
        this.nbAllumettesSelectionnees = nbAllumettesSelectionnees;
        invalidate(); // Redessinez la vue avec les nouvelles allumettes sélectionnées
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int nbAllumettes = this.nbAllumettesVisibles;
        int x = espaceBordEcran;
        int y = (getHeight() - allumetteHauteur) / 2; // Centrez verticalement les allumettes

        while (nbAllumettes > 0) {
            allumette.setBounds(x, y, x + allumetteLargeur, y + allumetteHauteur);
            allumette.draw(canvas);

            if (nbAllumettes <= nbAllumettesSelectionnees) {
                Rect rect = new Rect(x - 5, y - 5, x + allumetteLargeur + 5, y + allumetteHauteur + 5);
                canvas.drawRect(rect, selectionPaint);
            }

            x += allumetteLargeur + espaceEntreAllumettes;

            --nbAllumettes;
        }
    }


}
