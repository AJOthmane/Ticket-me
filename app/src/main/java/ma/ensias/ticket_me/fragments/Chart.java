package ma.ensias.ticket_me.fragments;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.activities.StatsActivity;
import ma.ensias.ticket_me.model.Ticket;

public class Chart extends Fragment {

    int type;
    LinkedList<Ticket> tickets;
    int[][] data = {{13, 45}, {15, 50}, {17, 35}};
    LineChart mChart;

    public Chart(int type, LinkedList<Ticket> tickets) {
        this.type = type;
        this.tickets = tickets;
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
        Toast.makeText(getContext(), "" + type, Toast.LENGTH_SHORT).show();

        List<Entry> entries = new ArrayList<Entry>();
        for (int i=0; i<data.length; i++) {
            entries.add(new Entry(data[i][0], data[i][1]));
        }

        mChart = chartView.findViewById(R.id.chart);

        LineDataSet dataSet = new LineDataSet(entries, "Label");
        dataSet.setColor(255);
        dataSet.setValueTextColor(000);

        LineData lineData = new LineData(dataSet);
//        mChart.setData(lineData);
//        mChart.setContentDescription("My Chart");
        mChart.animateXY(1000, 1000);
        renderData(); // refresh

        return chartView;
    }

    public void renderData() {
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);

        final String xVal[]={"Mon","Tues","Wed","Thurs","Fri","Sat","Sun"};
        final String xVal1[]={"Week1","","Week2","","Week3","","Week4"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVal1));
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

    private void setData() {

        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(0, 250));
        values.add(new Entry(1, 50));
        values.add(new Entry(2, 100));
        values.add(new Entry(3, 80));
        values.add(new Entry(4, 120));
        values.add(new Entry(5, 110));
        values.add(new Entry(6, 150));

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