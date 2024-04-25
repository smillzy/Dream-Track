am5.ready(function () {

    ///////////////////// Pie ///////////////////

    let root1 = am5.Root.new("piediv");

    // Set themes
    // https://www.amcharts.com/docs/v5/concepts/themes/
    let myTheme = am5.Theme.new(root1);
    myTheme.rule("Label").setAll({
        fontSize: "25px",
    });

    root1.setThemes([am5themes_Animated.new(root1), myTheme]);

    // Create chart
    // https://www.amcharts.com/docs/v5/charts/percent-charts/pie-chart/
    let chartPie = root1.container.children.push(
        am5percent.PieChart.new(root1, {
            radius: am5.percent(90),
            innerRadius: am5.percent(50),
            layout: root1.horizontalLayout,
        })
    );

    // Create series
    // https://www.amcharts.com/docs/v5/charts/percent-charts/pie-chart/#Series
    let seriesPie = chartPie.series.push(
        am5percent.PieSeries.new(root1, {
            name: "Series",
            valueField: "expense",
            categoryField: "item",
        })
    );

    // Set data
    // https://www.amcharts.com/docs/v5/charts/percent-charts/pie-chart/#Setting_data
    seriesPie.data.setAll(balanceSheet);

    // Disabling labels and ticks
    seriesPie.labels.template.set("visible", false);
    seriesPie.ticks.template.set("visible", false);

    // Adding gradients
    seriesPie.slices.template.set("strokeOpacity", 0);
    seriesPie.slices.template.set(
        "fillGradient",
        am5.RadialGradient.new(root1, {
            stops: [
                {
                    brighten: -0.1,
                },
                {
                    brighten: -0.1,
                },
                {
                    brighten: -0.1,
                },
                {
                    brighten: 1.1,
                },
                {
                    brighten: -0.1,
                },
            ],
        })
    );

    // Create legend
    // https://www.amcharts.com/docs/v5/charts/percent-charts/legend-percent-series/
    let legendPie = chartPie.children.push(
        am5.Legend.new(root1, {
            centerY: am5.percent(50),
            y: am5.percent(50),
            layout: root1.verticalLayout,
        })
    );
    // set value labels align to right
    legendPie.valueLabels.template.setAll({textAlign: "right"});
    // set width and max width of labels
    legendPie.labels.template.setAll({
        maxWidth: 140,
        width: 140,
        oversizedBehavior: "wrap",
    });

    legendPie.data.setAll(seriesPie.dataItems);

    // Play initial series animation
    // https://www.amcharts.com/docs/v5/concepts/animations/#Animation_of_series
    seriesPie.appear(1000, 100);

    ///////////////// line ////////////////////
    // let rootline = am5.Root.new("linediv");
    //
    // rootline.setThemes([am5themes_Animated.new(rootline)]);
    //
    // let chartline = rootline.container.children.push(
    //     am5xy.XYChart.new(rootline, {
    //         panX: true,
    //         panY: true,
    //         wheelX: "panX",
    //         wheelY: "zoomX",
    //         pinchZoomX: true,
    //     })
    // );
    //
    // // chartline.get("colors").set("step", 3);
    //
    // let cursorline = chartline.set("cursor", am5xy.XYCursor.new(rootline, {}));
    // cursorline.lineY.set("visible", false);
    //
    // let xAxisline = chartline.xAxes.push(
    //     am5xy.DateAxis.new(rootline, {
    //         maxDeviation: 0.3,
    //         baseInterval: {
    //             timeUnit: "day",
    //             count: 1,
    //         },
    //         renderer: am5xy.AxisRendererX.new(rootline, {minorGridEnabled: true}),
    //         tooltip: am5.Tooltip.new(rootline, {}),
    //     })
    // );
    //
    // let yAxisline = chartline.yAxes.push(
    //     am5xy.ValueAxis.new(rootline, {
    //         maxDeviation: 0.3,
    //         renderer: am5xy.AxisRendererY.new(rootline, {}),
    //     })
    // );
    //
    // let seriesline = chartline.series.push(
    //     am5xy.LineSeries.new(rootline, {
    //         name: "Series 1",
    //         xAxis: xAxisline,
    //         yAxis: yAxisline,
    //         valueXField: "date",
    //         valueYField: "net_income",
    //         tooltip: am5.Tooltip.new(rootline, {
    //             labelText: "{valueX}: {valueY}",
    //         }),
    //     })
    // );
    //
    // seriesline.data.processor = am5.DataProcessor.new(rootline, {
    //     dateFormat: "yyyy-MM-dd",
    //     dateFields: ["date"]
    // });
    //
    // seriesline.strokes.template.setAll({
    //     strokeWidth: 2,
    // });
    //
    // seriesline.get("tooltip").get("background").set("fillOpacity", 0.5);
    //
    // rootline.dateFormatter.setAll({
    //     dateFormat: "yyyy-MM-dd",
    //     dateFields: ["valueX"],
    // });
    //
    // let dataline = netIncomeData;
    //
    // seriesline.data.setAll(dataline);
    //
    // seriesline.appear(1000);
    // chartline.appear(1000, 100);
}); // end am5.ready()
