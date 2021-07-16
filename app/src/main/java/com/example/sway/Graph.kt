package com.example.sway

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.XAxis
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries


class Graph : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var graphView: GraphView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val userid = auth.currentUser.uid
        graphView = findViewById(R.id.idGraphView)
       // val series = LineGraphSeries(arrayOf(DataPoint(0.0, 1.0), DataPoint(1.0, 5.0), DataPoint(2.0, 3.0)))

        //var graph:BarChart=findViewById(R.id.barchart)
        db.collection("dailygraph").document(userid).get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists","DocumentSnapshot Data:${document.data}")
                var chart = document["daily"] as List<String>
                var length=chart.count()-1
                Log.d("TAG",length.toString())
//                var visitors=ArrayList<BarEntry>()
//                var labels = ArrayList<String>()
                val arr: Array<DataPoint?> = arrayOfNulls<DataPoint>(length+1)
                for (i in 0..length){
                    arr[i]=DataPoint((i+1).toDouble(),chart[i].toDouble())
//                    visitors.add(BarEntry(chart[i].toFloat(),i.toFloat() ))
//                    labels.add("day$i")
                    Log.d("TAG",arr.toString())
//                    Log.d("TAG",labels.toString())
                }
                val series = LineGraphSeries(arr)
                graphView.title = "My Graph View"
                graphView.titleColor = R.color.purple_200
                graphView.titleTextSize = 18F
                graphView.addSeries(series)
                graphView.animate()
                graphView.viewport.setMinX(1.0);
                graphView.viewport.setMaxX((length+1).toDouble());
                graphView.viewport.setMinY(100.0);
                graphView.viewport.setMaxY(500.0);

                graphView.viewport.isYAxisBoundsManual = true;
                graphView.viewport.isXAxisBoundsManual = true;
//                val xAxis: XAxis = graphView.getXAxis()
//                xAxis.granularity = 1f
//                val staticLabelsFormatter = StaticLabelsFormatter(graphView)
//                staticLabelsFormatter.setHorizontalLabels(arrayOf("1", "2", "3", "4", "5"))
//                graphView.gridLabelRenderer.labelFormatter = staticLabelsFormatter
                //var set1 = BarDataSet(visitors, "Cells")
                //var set2 = BarDataSet(labels, "Days")
                //var bardata=BarData(set2,set1)
                
                //set1 = BarDataSet(yVals1, "The year 2017")
//                set1.setColors(*ColorTemplate.MATERIAL_COLORS)
//
//                val dataSets = ArrayList<IBarDataSet>()
//                dataSets.add(set1)
//
//                val data = BarData(dataSets)
//
//                data.setValueTextSize(10f)
//                data.barWidth = 0.9f
//
//                graph.setTouchEnabled(false)
//                graph.setData(data)

//                var data=BarData(labels,barDataSet)

                //val data:BarData = BarData(labels, barDataSet)
//                barChart.data = data // set the data and list of lables into chart
//
//                barChart.setDescription("Set Bar Chart Description")  // set the description
//
//                //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
//                barDataSet.color = resources.getColor(R.color.colorAccent)
//
//                barChart.animateY(5000)
                Log.d("TAG", chart.toString())
            }
            else{
                Toast.makeText(this, "No such document", Toast.LENGTH_LONG).show()
            }
        }  .addOnFailureListener { exception ->
            Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()

        }
//        for ()
//        visitors.
    }
}