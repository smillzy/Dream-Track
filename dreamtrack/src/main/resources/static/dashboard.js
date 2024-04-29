const monthBtn = document.getElementById("time-wrapper-filter-month");
const halfMonthBtn = document.getElementById("time-wrapper-filter-half-month");
const yearBtn = document.getElementById("time-wrapper-filter-year");
const expenseBtn = document.getElementById("title-wrapper-expense");
const revenueBtn = document.getElementById("title-wrapper-revenue");
const detailTable = document.getElementById("detail-wrapper-group");
const descriptionTable = document.getElementById("description-wrapper-group");

const selectedDetail = (showedElement, hideElement) => {
    showedElement.classList.add("selected");
    if (Array.isArray(hideElement)) {
        hideElement.forEach((element) => element.classList.remove("selected"));
    } else if (hideElement) {
        hideElement.classList.remove("selected");
    }
};

const hideElement = (showElements, hideElements) => {
    showElements.forEach(element => element.classList.remove("hide"));
    hideElements.forEach(element => element.classList.add("hide"));
}

monthBtn.addEventListener("click", () => {
    selectedDetail(monthBtn, [halfMonthBtn, yearBtn]);
    hideElement([], [detailTable, descriptionTable]);
    [expenseBtn, revenueBtn].forEach(btn => btn.classList.remove("selected"));
});

halfMonthBtn.addEventListener("click", () => {
    selectedDetail(halfMonthBtn, [yearBtn, monthBtn]);
    hideElement([], [detailTable, descriptionTable]);
    [expenseBtn, revenueBtn].forEach(btn => btn.classList.remove("selected"));
});

yearBtn.addEventListener("click", () => {
    selectedDetail(yearBtn, [halfMonthBtn, monthBtn]);
    hideElement([], [detailTable, descriptionTable]);
    [expenseBtn, revenueBtn].forEach(btn => btn.classList.remove("selected"));
});

expenseBtn.addEventListener("click", () => {
    selectedDetail(expenseBtn, revenueBtn);
    hideElement([detailTable], [descriptionTable]);
});

revenueBtn.addEventListener("click", () => {
    selectedDetail(revenueBtn, expenseBtn);
    hideElement([detailTable], [descriptionTable]);
});

detailTable.addEventListener("click", () => {
    descriptionTable.classList.remove("hide");
});

const timeDetail = document.getElementById("time-wrapper-time-detail");
const leftArrow = document.getElementById("time-wrapper-time-left");

const formatNumber = (num) => ("0" + num).slice(-2);

const updateTimeDetail = (start, end) => {
    if (end) {
        timeDetail.textContent = `${start.getFullYear()}年 ${formatNumber(
            start.getMonth() + 1
        )}月 ~ ${end.getFullYear()}年 ${formatNumber(end.getMonth() + 1)}月`;
    } else {
        timeDetail.textContent = `${start.getFullYear()}年 ${formatNumber(
            start.getMonth() + 1
        )}月`;
    }
};

let currentType;

monthBtn.addEventListener("click", () => {
    currentType = "month";
    const now = new Date(2024, 3); // 2024年4月
    updateTimeDetail(now);
});

halfMonthBtn.addEventListener("click", () => {
    currentType = "half-month";
    const now = new Date(2024, 3); // 设置为2024年4月的Date对象
    const sixMonthsAgo = new Date(now.getFullYear(), now.getMonth() - 5, 1); // 计算六个月前
    updateTimeDetail(sixMonthsAgo, now);
});

yearBtn.addEventListener("click", () => {
    currentType = "year";
    const now = new Date();
    timeDetail.textContent = `${now.getFullYear()}年`;
});

leftArrow.addEventListener("click", () => {
    const parts = timeDetail.textContent.split(" ~ ");
    const start = new Date(parts[0].replace(/年|月/g, "/"));
    const end = parts[1] ? new Date(parts[1].replace(/年|月/g, "/")) : null;

    if (currentType === "month") {
        start.setMonth(start.getMonth() - 1);
        updateTimeDetail(start);
    } else if (currentType === "half-month") {
        start.setMonth(start.getMonth() - 6);
        end.setMonth(end.getMonth() - 6);
        updateTimeDetail(start, end);
    } else if (currentType === "year") {
        start.setFullYear(start.getFullYear() - 1);
        timeDetail.textContent = `${start.getFullYear()}年`;
    }
});

const rightArrow = document.getElementById("time-wrapper-time-right");


rightArrow.addEventListener("click", () => {
    const parts = timeDetail.textContent.split(" ~ ");
    const start = new Date(parts[0].replace(/年|月/g, "/"));
    const end = parts[1] ? new Date(parts[1].replace(/年|月/g, "/")) : null;

    if (currentType === "month") {
        start.setMonth(start.getMonth() + 1);
        updateTimeDetail(start);
    } else if (currentType === "half-month") {
        start.setMonth(start.getMonth() + 6);
        end.setMonth(end.getMonth() + 6);
        updateTimeDetail(start, end);
    } else if (currentType === "year") {
        start.setFullYear(start.getFullYear() + 1);
        timeDetail.textContent = `${start.getFullYear()}年`;
    }
});

// Action

function animateBudget() {
    const budgetBar = document.getElementById("budget-wrapper-bar");
    let currentValue = 0;
    const maxValue = 5000;
    const totalValue = 10000;
    const duration = 1200; // 动画总时长（毫秒）
    const stepTime = 20; // 每步时间间隔（毫秒）

    const steps = duration / stepTime; // 计算总步数
    const increment = maxValue / steps; // 每步增加的金额

    const interval = setInterval(() => {
        currentValue += increment;
        if (currentValue >= maxValue) {
            currentValue = maxValue;
            clearInterval(interval);
        }

        const percentage = (currentValue / totalValue) * 100;
        budgetBar.style.backgroundImage = `linear-gradient(to right, rgb(106, 205, 255) ${percentage}%, #ccc ${percentage}%)`;
        budgetBar.textContent = `剩餘預算 $ ${Math.round(
            currentValue
        )} / $ ${totalValue}`;
    }, stepTime);
}

window.onload = animateBudget;

document.addEventListener('DOMContentLoaded', function () {
    const ctxDoughnut = document.getElementById('myDoughnutChart').getContext('2d');
    const myDoughnutChart = new Chart(ctxDoughnut, {
        type: 'doughnut',
        data: {
            datasets: [{
                data: [300, 200],
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 205, 86)',
                    'rgb(75, 192, 192)',
                    'rgb(153, 102, 255)'
                ]
            }]
        },
        options: {
            responsive: true, // Make the chart responsive
            plugins: {
                legend: {
                    position: 'top', // Position of the legend
                }
            }
        }
    });
});

document.addEventListener('DOMContentLoaded', function () {
    // Context for the Doughnut chart
    const ctxDoughnut = document.getElementById('pie-expense-chart').getContext('2d');
    const myDoughnutChart = new Chart(ctxDoughnut, {
        type: 'doughnut', // Specify the chart type
        data: {
            labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple'], // Categories
            datasets: [{
                label: 'My First Dataset',
                data: [300, 200, 100, 50, 50], // Data points
                backgroundColor: [ // Colors for each section
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 205, 86)',
                    'rgb(75, 192, 192)',
                    'rgb(153, 102, 255)'
                ]
            }]
        },
        options: {
            responsive: true, // Make the chart responsive
            plugins: {
                legend: {
                    position: 'top', // Position of the legend
                }
            }
        }
    });
});

document.addEventListener('DOMContentLoaded', function () {
    // Context for the Doughnut chart
    const ctxDoughnut = document.getElementById('pie-revenue-chart').getContext('2d');
    const myDoughnutChart = new Chart(ctxDoughnut, {
        type: 'doughnut', // Specify the chart type
        data: {
            labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple'], // Categories
            datasets: [{
                label: 'My First Dataset',
                data: [300, 200, 100, 50, 50], // Data points
                backgroundColor: [ // Colors for each section
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 205, 86)',
                    'rgb(75, 192, 192)',
                    'rgb(153, 102, 255)'
                ]
            }]
        },
        options: {
            responsive: true, // Make the chart responsive
            plugins: {
                legend: {
                    position: 'top', // Position of the legend
                }
            }
        }
    });
});