/**
 * Function to load vacancies from the server and display them in the table.
 */
let currentPage = 0;
let pageSize = 10;
let lastFilters = {};

function fetchVacancies(filters = {}, page = currentPage) {
    const progressBar = document.querySelector('#progressBar');
    progressBar.value = 0;
    statusText.innerText = 'Loading...';

    // Save filters and page
    lastFilters = filters;
    currentPage = page;

    // Build query string from filters and pagination
    const paramsObj = { ...filters, page: currentPage, size: pageSize };
    const params = new URLSearchParams(paramsObj).toString();
    const url = `/update-vacancies?${params}`;

    fetch(url, {
        method: 'GET'
    })
    .then(response => response.json())
    .then(result => {
        const data = result.data || [];
        const total = result.total || 0;
        const page = result.page || 0;
        const size = result.size || pageSize;

        const tbody = document.querySelector('#vacanciesTable tbody');
        tbody.innerHTML = '';
        progressBar.value = 50;

        data.forEach(vacancy => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${vacancy.title}</td>
                <td>${vacancy.employer}</td>
                <td>${vacancy.location}</td>
                <td>${vacancy.employment}</td>
                <td>${formatSalary(vacancy.salary)}</td>
                <td>${formatDate(vacancy.publishedAt)}</td>
                <td>${vacancy.source}</td>
                <td><a href="${vacancy.link}" target="_blank">HH.ru</a></td>
            `;
            tbody.appendChild(row);
        });

        renderPaginationControls(page, size, total);

        progressBar.value = 100;
        statusText.innerText = '';
    })
    .catch(error => {
        console.error('Error loading data:', error);
        progressBar.value = 0;
        statusText.innerText = 'Error loading data';
    });
}

/**
 * Function to format salary.
 * @param {string|null|undefined} salary - Salary to format.
 * @returns {string} - Formatted salary.
 */
function formatSalary(salary) {
    if (salary === null || salary === undefined) {
        return 'Not specified';
    } else {
        // Remove any existing currency symbol and add it back
        const cleanSalary = salary.replace(/[₽\s]/g, '').trim();
        if (cleanSalary === 'None' || cleanSalary === '') {
            return 'Not specified';
        }
        return `${cleanSalary} ₽`;
    }
}

/**
 * Function to create a table row.
 * @param {Object} vacancy - Vacancy data.
 * @returns {HTMLTableRowElement} - Table row.
 */
function createTableRow(vacancy) {
    const row = document.createElement('tr');
    row.innerHTML = `
        <td>${vacancy.title}</td>
        <td>${vacancy.employer}</td>
        <td>${vacancy.location}</td>
        <td>${vacancy.employment}</td>
        <td>${formatSalary(vacancy.salary)}</td>
        <td>${formatDate(vacancy.publishedAt)}</td>
        <td>${vacancy.source}</td>
        <td><a href="${vacancy.link}" target="_blank">Открыть</a></td>
    `;
    return row;
}

function renderPaginationControls(page, size, total) {
    const controls = document.getElementById('paginationControls');
    controls.innerHTML = '';
    const totalPages = Math.ceil(total / size);
    if (totalPages <= 1) return;

    // Previous button
    const prevBtn = document.createElement('button');
    prevBtn.textContent = '«';
    prevBtn.disabled = page === 0;
    prevBtn.onclick = () => fetchVacancies(lastFilters, page - 1);
    controls.appendChild(prevBtn);

    // Page numbers
    for (let i = 0; i < totalPages; i++) {
        const btn = document.createElement('button');
        btn.textContent = (i + 1).toString();
        btn.disabled = i === page;
        btn.onclick = () => fetchVacancies(lastFilters, i);
        controls.appendChild(btn);
    }

    // Next button
    const nextBtn = document.createElement('button');
    nextBtn.textContent = '»';
    nextBtn.disabled = page >= totalPages - 1;
    nextBtn.onclick = () => fetchVacancies(lastFilters, page + 1);
    controls.appendChild(nextBtn);
}

function applyFilters() {
    currentPage = 0;
    const filters = {
        title: document.getElementById('searchTitle').value,
        city: document.getElementById('filterCity').value,
        company: document.getElementById('filterCompany').value,
        salary: document.getElementById('filterSalary').value,
        employment: document.getElementById('filterEmployment').value,
        description: document.getElementById('filterDescription').value
    };
    fetchVacancies(filters, 0);
}

function resetFilters() {
    document.getElementById('searchTitle').value = '';
    document.getElementById('filterCity').value = '';
    document.getElementById('filterCompany').value = '';
    document.getElementById('filterSalary').value = '';
    document.getElementById('filterEmployment').value = '';
    document.getElementById('filterDescription').value = '';
    currentPage = 0;
    fetchVacancies({}, 0);
}

function formatDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    if (isNaN(date)) return dateString;
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
} 