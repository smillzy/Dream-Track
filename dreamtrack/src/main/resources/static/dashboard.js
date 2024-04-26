am5.ready(function () {
    // Create root element
    // https://www.amcharts.com/docs/v5/getting-started/#Root_element
    let root = am5.Root.new("chartdiv");

    // Set themes
    // https://www.amcharts.com/docs/v5/concepts/themes/
    root.setThemes([am5themes_Animated.new(root)]);

    // Create chart
    // https://www.amcharts.com/docs/v5/charts/xy-chart/
    let chart = root.container.children.push(
        am5xy.XYChart.new(root, {
            panX: true,
            panY: false,
            wheelX: "panX",
            wheelY: "zoomX",
            paddingLeft: 0,
            layout: root.verticalLayout,
        })
    );

    // Add scrollbar
    // https://www.amcharts.com/docs/v5/charts/xy-chart/scrollbars/
    chart.set(
        "scrollbarX",
        am5.Scrollbar.new(root, {
            orientation: "horizontal",
        })
    );

    var data = barEvents;

    // Create axes
    // https://www.amcharts.com/docs/v5/charts/xy-chart/axes/
    var xRenderer = am5xy.AxisRendererX.new(root, {
        minGridDistance: 70,
        minorGridEnabled: true,
    });

    var xAxis = chart.xAxes.push(
        am5xy.CategoryAxis.new(root, {
            categoryField: "name",
            renderer: xRenderer,
            tooltip: am5.Tooltip.new(root, {
                themeTags: ["axis"],
                animationDuration: 200,
            }),
        })
    );

    xRenderer.grid.template.setAll({
        location: 1,
    });

    xAxis.data.setAll(data);

    var yAxis = chart.yAxes.push(
        am5xy.ValueAxis.new(root, {
            min: 0,
            renderer: am5xy.AxisRendererY.new(root, {
                strokeOpacity: 0.1,
            }),
        })
    );

    // Add series
    // https://www.amcharts.com/docs/v5/charts/xy-chart/series/

    var series0 = chart.series.push(
        am5xy.ColumnSeries.new(root, {
            name: "Income",
            xAxis: xAxis,
            yAxis: yAxis,
            valueYField: "last_month",
            categoryXField: "name",
            clustered: false,
            tooltip: am5.Tooltip.new(root, {
                labelText: "last_month: {valueY}",
            }),
        })
    );

    series0.columns.template.setAll({
        width: am5.percent(80),
        tooltipY: 0,
        strokeOpacity: 0,
    });

    series0.data.setAll(data);

    var series1 = chart.series.push(
        am5xy.ColumnSeries.new(root, {
            name: "Income",
            xAxis: xAxis,
            yAxis: yAxis,
            valueYField: "this_month",
            categoryXField: "name",
            clustered: false,
            tooltip: am5.Tooltip.new(root, {
                labelText: "this_month: {valueY}",
            }),
        })
    );

    series1.columns.template.setAll({
        width: am5.percent(50),
        tooltipY: 0,
        strokeOpacity: 0,
    });

    series1.data.setAll(data);

    var cursor = chart.set("cursor", am5xy.XYCursor.new(root, {}));

    // Make stuff animate on load
    // https://www.amcharts.com/docs/v5/concepts/animations/
    chart.appear(1000, 100);
    series0.appear();
    series1.appear();

    ///////////////////// Pie ///////////////////

    var root1 = am5.Root.new("piediv");

    // Set themes
    // https://www.amcharts.com/docs/v5/concepts/themes/
    root1.setThemes([am5themes_Animated.new(root1)]);

    // Create chart
    // https://www.amcharts.com/docs/v5/charts/percent-charts/pie-chart/
    var chartPie = root1.container.children.push(
        am5percent.PieChart.new(root1, {
            radius: am5.percent(90),
            innerRadius: am5.percent(50),
            layout: root1.horizontalLayout,
        })
    );

    // Create series
    // https://www.amcharts.com/docs/v5/charts/percent-charts/pie-chart/#Series
    var seriesPie = chartPie.series.push(
        am5percent.PieSeries.new(root1, {
            name: "Series",
            valueField: "amount",
            categoryField: "name",
        })
    );

    // Set data
    // https://www.amcharts.com/docs/v5/charts/percent-charts/pie-chart/#Setting_data
    seriesPie.data.setAll(pieEvents);

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
    var legendPie = chartPie.children.push(
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
}); // end am5.ready()
