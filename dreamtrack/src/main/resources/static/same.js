// Home page
document.addEventListener("DOMContentLoaded", () => {
    const home = document.getElementById("logo");
    if (home) { // 确保元素存在
        home.addEventListener("click", () => {
            window.location.href = '/index';
        });
    } else {
        console.error('Logo element not found');
    }
});

// accounting
const accounting = document.getElementById("function-list-accounting");

accounting.addEventListener("click", () => {
    window.location.href = '/index';
});


// dashboard
const expenseAnalyze = document.getElementById("function-list-expense-analyze");

expenseAnalyze.addEventListener("click", () => {
    window.location.href = '/dashboard';
});