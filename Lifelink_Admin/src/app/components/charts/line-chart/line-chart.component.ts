import { Component, ViewChild } from "@angular/core";
import {
  ChartComponent,
  ApexChart,
  ApexAxisChartSeries,
  ApexTitleSubtitle,
  ApexDataLabels,
  ApexFill,
  ApexYAxis,
  ApexXAxis,
  ApexTooltip,
  ApexMarkers,
  ApexAnnotations,
  ApexStroke,
  NgApexchartsModule
} from "ng-apexcharts";
import { data } from "./series-data";

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  dataLabels: ApexDataLabels;
  markers: ApexMarkers;
  title: ApexTitleSubtitle;
  fill: ApexFill;
  yaxis: ApexYAxis | ApexYAxis[];
  xaxis: ApexXAxis;
  tooltip: ApexTooltip;
  stroke: ApexStroke;
  annotations: ApexAnnotations;
  colors: string[];
  grid?: {
    show: boolean;
  };
};

type UpdateOptionsKey = "1m" | "6m" | "1y" | "1yd" | "all";

@Component({
  selector: "app-line-chart",
  standalone: true,
  imports: [NgApexchartsModule],
  template: `
    <button (click)="updateOptions('1m')" [class.active]="activeOptionButton === '1m'">1M</button>
    <button (click)="updateOptions('6m')" [class.active]="activeOptionButton === '6m'">6M</button>
    <button (click)="updateOptions('1y')" [class.active]="activeOptionButton === '1y'">1Y</button>
    <button (click)="updateOptions('1yd')" [class.active]="activeOptionButton === '1yd'">1YD</button>
    <button (click)="updateOptions('all')" [class.active]="activeOptionButton === 'all'">All</button>
    <apx-chart
      #chart
      [series]="chartOptions.series"
      [chart]="chartOptions.chart"
      [colors]="chartOptions.colors"
      [yaxis]="chartOptions.yaxis"
      [dataLabels]="chartOptions.dataLabels"
      [markers]="chartOptions.markers"
      [stroke]="chartOptions.stroke"
      [xaxis]="chartOptions.xaxis"
      [tooltip]="chartOptions.tooltip"
      [annotations]="chartOptions.annotations"
    ></apx-chart>
  `,
  styleUrls: ["./line-chart.component.scss"]
})
export class LineChartComponent {
  @ViewChild("chart", { static: false }) chart: ChartComponent | undefined;
  public chartOptions!: ChartOptions;
  public activeOptionButton: UpdateOptionsKey = "all";
  public updateOptionsData = {
    "1m": {
      xaxis: {
        min: new Date("28 Jan 2013").getTime(),
        max: new Date("27 Feb 2013").getTime()
      }
    },
    "6m": {
      xaxis: {
        min: new Date("27 Sep 2012").getTime(),
        max: new Date("27 Feb 2013").getTime()
      }
    },
    "1y": {
      xaxis: {
        min: new Date("27 Feb 2012").getTime(),
        max: new Date("27 Feb 2013").getTime()
      }
    },
    "1yd": {
      xaxis: {
        min: new Date("01 Jan 2013").getTime(),
        max: new Date("27 Feb 2013").getTime()
      }
    },
    all: {
      xaxis: {
        min: undefined,
        max: undefined
      }
    }
  };

  constructor() {
    this.initChart();
  }

  initChart(): void {
    this.chartOptions = {
      series: [
        {
          data: data
        }
      ],
      chart: {
        type: "area",
        height: 150,
        toolbar: {
          show: true
        }
      },
      colors: ["#00E396"],
      title: {
        text: "Line Chart",
        align: "left"
      },
      yaxis: {
        title: {
          text: "Values"
        }
      },
      stroke: {
        curve: "smooth",
        width: 2
      },
      annotations: {
        yaxis: [
          {
            y: 30,
            borderColor: "#999",
            label: {
              text: "Support",
              style: {
                color: "#fff",
                background: "#00E396"
              }
            }
          }
        ],
        xaxis: [
          {
            x: new Date("14 Nov 2012").getTime(),
            borderColor: "#999",
            label: {
              text: "Rally",
              style: {
                color: "#fff",
                background: "#775DD0"
              }
            }
          }
        ]
      },
      dataLabels: {
        enabled: false
      },
      markers: {
        size: 0
      },
      xaxis: {
        type: "datetime",
        min: new Date("01 Mar 2012").getTime(),
        tickAmount: 6
      },
      tooltip: {
        x: {
          format: "dd MMM yyyy"
        }
      },
      fill: {
        type: "gradient",
        gradient: {
          shadeIntensity: 1,
          opacityFrom: 0.7,
          opacityTo: 0.9,
          stops: [0, 100]
        }
      }
    };
  }

  public updateOptions(option: UpdateOptionsKey): void {
    this.activeOptionButton = option;
    this.chart?.updateOptions(this.updateOptionsData[option], false, true, true);
  }
}