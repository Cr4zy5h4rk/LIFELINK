import { Component, ViewChild } from "@angular/core";
import {
  ApexAxisChartSeries,
  ApexChart,
  ChartComponent,
  ApexDataLabels,
  ApexPlotOptions,
  ApexLegend,
  NgApexchartsModule
} from "ng-apexcharts";

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  dataLabels: ApexDataLabels;
  plotOptions: ApexPlotOptions;
  legend: ApexLegend;
  colors: string[];
  title: ApexTitleSubtitle
};

@Component({
  selector: "app-reversed-chart",
  standalone: true,
  imports: [NgApexchartsModule],
  template:`<div id="chart">
  <apx-chart
    [series]="chartOptions.series"
    [chart]="chartOptions.chart"
    [title]="chartOptions.title"
    [legend]="chartOptions.legend"
    [dataLabels]="chartOptions.dataLabels"
    [colors]="chartOptions.colors"
    [plotOptions]="chartOptions.plotOptions"
  ></apx-chart>
</div>`,
  styleUrls: ["./reversed-chart.component.scss"]
})
export class ReversedChartComponent {
  @ViewChild("chart") chart: ChartComponent | undefined;
  public chartOptions: ChartOptions;

  constructor() {
    this.chartOptions = {
      title: {
        text: "Quantite de poches recoltee",
        align: 'center',
        margin: 10,
        offsetX: 0,
        offsetY: 0,
        floating: false,
        style: {
          fontSize:  '14px',
          fontWeight:  'bold',
          fontFamily:  undefined,
          color:  '#263238'
        },
    },
      series: [
        {
          name: "Actuelle",
          data: [
            {
              x: "2024",
              y: 1292,
              goals: [
                {
                  name: "Cible",
                  value: 1400,
                  strokeWidth: 5,
                  strokeColor: "#775DD0"
                }
              ]
            },
            {
              x: "2025",
              y: 6432,
              goals: [
                {
                  name: "Cible",
                  value: 5400,
                  strokeWidth: 5,
                  strokeColor: "#775DD0"
                }
              ]
            },
          ]
        }
      ],
      chart: {
        height: 150,

        type: "bar"
      },
      plotOptions: {
        bar: {
          horizontal: true
        }
      },
      colors: ["#00E396"],
      dataLabels: {
        formatter: function (val, opts) {
          const goals =
            opts.w.config.series[opts.seriesIndex].data[opts.dataPointIndex]
              .goals;
        
          if (goals && goals.length) {
            return `${val} / ${goals[0].value}`;
          }
          return val.toString();
        }
      },
      legend: {
        show: true,
        showForSingleSeries: true,
        customLegendItems: ["Actuelle", "Cible"],
        markers: {
          fillColors: ["#00E396", "#775DD0"]
        }
      }
    };
  }
}

