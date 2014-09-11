package com.shakenbeer.curtandray.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.AndroidGame;
import com.shakenbeer.curtandray.R;
import com.shakenbeer.curtandray.game.LoadingScreen;

public class CurtAndRayGame extends AndroidGame {

    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setMessage(R.string.quit)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CurtAndRayGame.this.finish();
                        }

                    }).setNegativeButton(android.R.string.no, null).show();

            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
