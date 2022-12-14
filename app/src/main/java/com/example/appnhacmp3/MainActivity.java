package com.example.appnhacmp3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtTitle,txtTimeSong,txtTimetotal;
    SeekBar skSong;
    ImageView imgHinh;
    ImageButton ibtnPrev,ibtnPlay,ibtnStop,ibtnNext;

    ArrayList<Song> arraySong;
    int position=0;
    MediaPlayer mediaPlayer;
    Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        AddSong();
        animation= AnimationUtils.loadAnimation(this,R.anim.disc_rotate);
        KhoiTaoMediaper();

        ibtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if(position<0){
                    position=arraySong.size()-1;

                }
                if (mediaPlayer.isPlaying()){// dang dc phat
                    mediaPlayer.stop();
                }
                KhoiTaoMediaper();
                mediaPlayer.start();
                ibtnPlay.setImageResource(R.drawable.pause_icon);
                SetTimeTotal();

            }
        });

        ibtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if(position>arraySong.size()-1){
                    position=0;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaper();
                mediaPlayer.start();
                ibtnPlay.setImageResource(R.drawable.pause_icon);
                SetTimeTotal();

            }
        });


        ibtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                ibtnPlay.setImageResource(R.drawable.play_icon);
                KhoiTaoMediaper();

            }
        });

        ibtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    // neu dang phat -> b???m pause-> doi hinh play
                    mediaPlayer.pause();
                    ibtnPlay.setImageResource(R.drawable.play_icon);
                }else {
                    // dang ngung -> b???m play-> doi hinh pause
                    mediaPlayer.start();
                    ibtnPlay.setImageResource(R.drawable.pause_icon);

                }
                SetTimeTotal();
                UpdatetimeSong();
                imgHinh.startAnimation(animation);

            }
        });
        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //keo se hat
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // vua cham se hat
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // cham xong tha tay se phat
                mediaPlayer.seekTo(skSong.getProgress());

            }
        });


        }


        private void   UpdatetimeSong(){

        // Handler d??ng ????? t???o th???i gian
            Handler handler= new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                SimpleDateFormat dinhDangGio= new SimpleDateFormat("mm:ss");
                txtTimeSong.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()));
              //udate progress sksong
                    skSong.setProgress(mediaPlayer.getCurrentPosition());

                //  kiem tra thoi gian hat -> neu ket thuc -  > next
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            position++;
                            if(position>arraySong.size()-1){
                                position=0;
                            }
                            if (mediaPlayer.isPlaying()){
                                mediaPlayer.stop();
                            }
                            KhoiTaoMediaper();
                            mediaPlayer.start();
                            ibtnPlay.setImageResource(R.drawable.pause_icon);
                            SetTimeTotal();
                        }
                    });
               handler.postDelayed(this,500);// l???p l???i t??? ?????ng t??ng sau n???a gi??y
                }
            },100);
        }
        private void KhoiTaoMediaper(){
            mediaPlayer= MediaPlayer.create(MainActivity.this,arraySong.get(position).getFile());
            txtTitle.setText(arraySong.get(position).getTitle());
       // gan max cua skSong = mediaplayer getDuration
            skSong.setMax(mediaPlayer.getDuration());

        }

    private void AddSong() {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("????? v????ng",R.raw.de_vuong));
        arraySong.add(new Song("Hai tri???u n??m",R.raw.hai_trieu_nam));//
        arraySong.add(new Song("C?????i th??i",R.raw.cuoi_thoi));
        arraySong.add(new Song("????? t???c 2",R.raw.do_toc2));
        arraySong.add(new Song("K??? c???p g???p b?? gi??",R.raw.ke_cap_gap_ba_gia));
        arraySong.add(new Song("Mu???n r???i sao c??n",R.raw.muon_roi_sao_con));
        arraySong.add(new Song("S??i g??n ??au l??ng qu??",R.raw.sai_gon_dau_long_qua));
        arraySong.add(new Song("So fa",R.raw.so_far));
        arraySong.add(new Song("Th?? l????ng",R.raw.the_luong));
        arraySong.add(new Song("Th???c gi???c",R.raw.thuc_giac));
        arraySong.add(new Song("Chuy???n r???ng",R.raw.chuyen_rang));
    }

    private void SetTimeTotal(){
        SimpleDateFormat dinhDangGio= new SimpleDateFormat("mm:ss");
        txtTimetotal.setText(dinhDangGio.format(mediaPlayer.getDuration()));//getDuration() lay con so trong bai hat
    }

    private void AnhXa() {
        txtTitle     =(TextView) findViewById(R.id.textViewTitle);
        txtTimeSong  =(TextView) findViewById(R.id.tvTimeSong);
        txtTimetotal =(TextView) findViewById(R.id.tvTimeTotal);
        skSong       =(SeekBar) findViewById(R.id.skSong);
        ibtnNext     =(ImageButton) findViewById(R.id.ibtnNext);
        ibtnPlay     =(ImageButton) findViewById(R.id.ibtnPlay);
        ibtnPrev     =(ImageButton) findViewById(R.id.ibtnPrev);
        ibtnStop     =(ImageButton) findViewById(R.id.itbnStop);
        imgHinh      =(ImageView) findViewById(R.id.imgHinh);
    }
}
