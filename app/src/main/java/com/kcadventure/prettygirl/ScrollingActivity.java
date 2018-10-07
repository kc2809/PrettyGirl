package com.kcadventure.prettygirl;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.kcadventure.prettygirl.service.GirlApiService;
import com.kcadventure.prettygirl.service.GirlsApi;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ScrollingActivity extends AppCompatActivity {
  private static final String GIRL_URL = "https://www.pic-th.com/ajax/load.php?action=load_all&page=2";

  CompositeDisposable disposable = new CompositeDisposable();

  private Observable<Document> serverJsoupObservable = Observable.create(emiter -> {
//    Document doc = Jsoup.connect(GIRL_URL).get();
    String html = "<html><head><title>First parse</title></head>"
        + "<body><p>Parsed HTML into a doc.</p></body></html>";
    Document doc = Jsoup.parse(html);
    emiter.onNext(doc);
    emiter.onComplete();
  });

  private GirlApiService girlSerice;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scrolling);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });

    Disposable subcribe = serverJsoupObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.io()).subscribe( document -> {
          doOnMainThread(document);
    });


    girlSerice = new GirlApiService();
    Observable<String> girlObservable = girlSerice.getRawHtmlAllGirls222();
    Disposable girlSubcribe =  girlObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe( str -> {
      testGIrlService(str);
    });

    disposable.add(subcribe);
    disposable.add(girlSubcribe);
  }

  private void doOnMainThread(Document document){
    System.out.println(document.text());
  }

  private void testGIrlService(String str){
    System.out.println(str);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_scrolling, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onStop() {
    super.onStop();
    if(disposable !=null && !disposable.isDisposed()){
      disposable.dispose();
    }
  }
}
