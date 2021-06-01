package ma.ensias.ticket_me.fragments;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.activities.StatsActivity;
import ma.ensias.ticket_me.api.APIClient;
import ma.ensias.ticket_me.api.APIInterface;
import ma.ensias.ticket_me.model.Ticket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chart extends Fragment {

    int type;
    int id_category;
    int id_event;
    LinkedList<Ticket> tickets;
    List<Ticket> consumedtickets;
    int[][] data = {{13, 45}, {15, 50}, {17, 35}};
    LineChart mChart;

    public Chart(int type, int id_event, int id_category) {
        this.type = type;
        this.id_event = id_event;
        this.id_category = id_category;
    }

    public Chart() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View chartView = inflater.inflate(R.layout.fragment_chart, container, false);

        //Recuperer le graph
        mChart = chartView.findViewById(R.id.chart);
        mChart.animateXY(1000, 1000);

        HashMap<String, Integer> infos = new HashMap<>();
        infos.put("id_category",id_category);
        infos.put("id_event", id_event);
        APIInterface apiInterface = APIClient.createService(APIInterface.class);
        Call<LinkedList<Ticket>> call = apiInterface.getTickets(infos);
        call.enqueue(new Callback<LinkedList<Ticket>>() {
            @Override
            public void onResponse(Call<LinkedList<Ticket>> call, Response<LinkedList<Ticket>> response) {
                if(response.code() == 200)
                {
                    tickets = response.body();
                    consumedtickets = new ArrayList<>();
                    for(Ticket ticket : tickets){
                        if(ticket.getDateofConsumed()!= null){
                            consumedtickets.add(ticket);
                        }
                    }
                    try {
                        renderData();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                if(response.code() == 500)
                {
                    Log.e("Error", "Error 500");
                }
            }
            @Override
            public void onFailure(Call<LinkedList<Ticket>> call, Throwable t) {
                Log.e("Stats ",t.getMessage());
            }
        });

        List<Entry> entries = new ArrayList<Entry>();
        for (int i=0; i<data.length; i++) {
            entries.add(new Entry(data[i][0], data[i][1]));
        }

        return chartView;
    }

    public void renderData() throws ParseException {
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);

        final String xVal0[]={"Mon","Tues","Wed","Thurs","Fri","Sat","Sun"};
        final String xVal1[]={"Week1","Week2","Week3","Week4"};
        final String xVal2[] = {"Semestre1" ,"","", "Semestre2"};

        switch(type){
            case 0:
                xAxis.setValueFormatter(new IndexAxisValueFormatter(xVal0));
                break;
            case 1:
                xAxis.setValueFormatter(new IndexAxisValueFormatter(xVal1));
                break;
            case 2:
                xAxis.setValueFormatter(new IndexAxisValueFormatter(xVal2));
                break;
            default:
                break;
        }

        xAxis.setDrawLimitLinesBehindData(true);


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
//        leftAxis.setAxisMaximum(350f);
        leftAxis.setAxisMinimum(0f);
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(false);

        mChart.getAxisRight().setEnabled(false);
        setData();
    }

    private void setData() throws ParseException {

        ArrayList<Entry> values = new ArrayList<>();
//
//        values.add(new Entry(0, 250));
//        values.add(new Entry(1, 50));
//        values.add(new Entry(2, 100));
//        values.add(new Entry(3, 80));
//        values.add(new Entry(4, 120));
//        values.add(new Entry(5, 110));
//        values.add(new Entry(6, 150));

        int length = consumedtickets.size();
        List<String> loadedData = new ArrayList<>();
        int[] count={0,0,0,0,0,0,0};
        for(int i=1; i<=length; i++){
            System.out.println(consumedtickets.get(length-i).getDateofConsumed());
            DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
            String date = formatter.format(consumedtickets.get(length-i).getDateofConsumed());
            loadedData.add(date);
            if(i==2){
                if(date.split("-")[0] == loadedData.get(i - 1).split("-")[0]){
                    count[0] += 1;
                }
            }
        }
        switch(type){
            case 0:
                values.add(new Entry(0, 3));
                values.add(new Entry(1, 5));
                values.add(new Entry(2, 1));
                values.add(new Entry(3, 6));
                values.add(new Entry(4, 3));
                values.add(new Entry(5, 9));
                values.add(new Entry(6, 7));
                break;
            case 1:
                values.add(new Entry(0, 3));
                values.add(new Entry(1, 5));
                values.add(new Entry(2, 1));
                values.add(new Entry(3, 6));
                break;
            case 2:
                values.add(new Entry(0, 9));
                values.add(new Entry(3, 0));
                break;
            default:
                break;
        }

        LineDataSet set1;
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "Tickets consommés");
            set1.setDrawIcons(false);
//            set1.enableDashedLine(10f, 5f, 0f);
//            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.DKGRAY);
            set1.setCircleColor(Color.DKGRAY);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
//            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

//            set1.setFillColor(Color.DKGRAY);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            mChart.setData(data);
        }
    }

}