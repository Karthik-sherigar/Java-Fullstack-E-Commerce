# Java Full-Stack E-Commerce

A full-stack e-commerce application built to demonstrate and strengthen my Java full-stack development skills.

This project combines a Spring Boot REST API with a React frontend to provide product management, product search, cart management, image uploads, checkout, and order management.

## Project Objective

The main objective of this project is to practice and demonstrate practical Java full-stack development skills, including:

- Building REST APIs with Java and Spring Boot
- Creating database-driven CRUD functionality
- Developing a responsive frontend using React
- Connecting frontend and backend through Axios and REST APIs
- Handling file uploads with multipart requests
- Managing application state for products and cart items
- Implementing search, checkout, and order workflows
- Debugging frontend-backend integration issues

## Features

### Product Management

- View all products
- View individual product details
- Add new products
- Update existing products
- Delete products
- Upload and display product images
- View product availability and stock quantity
- Categorize products

### Search

- Search products by keyword
- Search by product name, description, brand, or category
- Display live product suggestions while typing
- Navigate to a dedicated search-results page
- Display product images in search results

### Cart and Checkout

- Add products to cart
- Store cart data in local storage
- Increase and decrease item quantities
- Remove products from cart
- Display cart total
- Checkout using customer name and email
- Update stock after checkout

### Orders

- Place orders
- View placed orders
- View customer information
- View order status
- Expand an order to see its items, quantities, and total amount

### User Interface

- Responsive layout
- Modern Bootstrap-based design
- Product cards and detail pages
- Styled forms, tables, modal dialogs, and buttons
- Toast notifications
- Light and dark theme compatibility

## Tech Stack

### Backend

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- REST API
- Multipart file upload
- Maven
- Relational database configured in the backend application

### Frontend

- React 18
- Vite
- React Router DOM
- Axios
- Bootstrap
- React Bootstrap
- Bootstrap Icons
- React Toastify

## Project Structure

```text
java-fullstack-ecommerce/
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── ... controllers, services, repositories, entities
│   │       └── resources/
│   │           └── application.properties
│   ├── pom.xml
│   └── mvnw
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── assets/
│   │   ├── components/
│   │   │   ├── AddProduct.jsx
│   │   │   ├── Cart.jsx
│   │   │   ├── CheckoutPopup.jsx
│   │   │   ├── Home.jsx
│   │   │   ├── Navbar.jsx
│   │   │   ├── Order.jsx
│   │   │   ├── Product.jsx
│   │   │   ├── SearchResults.jsx
│   │   │   └── UpdateProduct.jsx
│   │   ├── Context/
│   │   ├── App.jsx
│   │   ├── main.jsx
│   │   └── index.css
│   ├── .env
│   ├── package.json
│   └── vite.config.js
└── README.md
```

## Application Flow

```text
React Frontend
      |
      | Axios HTTP requests
      v
Spring Boot REST API
      |
      | Spring Data JPA
      v
Database
```

## API Endpoints

| Method | Endpoint                                 | Description                  |
| ------ | ---------------------------------------- | ---------------------------- |
| GET    | `/api/products`                          | Fetch all products           |
| GET    | `/api/product/{id}`                      | Fetch one product by ID      |
| GET    | `/api/product/{id}/image`                | Fetch a product image        |
| POST   | `/api/product`                           | Add a new product with image |
| PUT    | `/api/product/{id}`                      | Update a product             |
| DELETE | `/api/product/{id}`                      | Delete a product             |
| GET    | `/api/products/search?keyword={keyword}` | Search products              |
| POST   | `/api/orders/place`                      | Place an order               |
| GET    | `/api/orders`                            | Fetch all orders             |

## Product Search

The backend supports keyword-based searches across multiple product fields:

- Product name
- Description
- Brand
- Category

Example request:

```http
GET /api/products/search?keyword=laptop
```

Example repository query:

```java
@Query("""
    SELECT p FROM Product p
    WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
List<Product> searchProducts(@Param("keyword") String keyword);
```

## Prerequisites

Install the following before running the project:

- Java Development Kit (JDK)
- Maven or Maven Wrapper
- Node.js
- npm
- A relational database supported by your Spring Boot configuration
- Git

Check installed versions:

```bash
java -version
node -v
npm -v
```

## Backend Setup

1. Navigate to the backend directory:

   ```bash
   cd backend
   ```

2. Configure the database connection in:

   ```text
   src/main/resources/application.properties
   ```

3. Add your database URL, username, password, and JPA configuration.

   Example configuration structure:

   ```properties
   spring.datasource.url=jdbc:<database-url>
   spring.datasource.username=<database-username>
   spring.datasource.password=<database-password>

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

   Replace the placeholders with your actual database settings.

4. Run the Spring Boot application:

   ```bash
   ./mvnw spring-boot:run
   ```

   Alternatively, if Maven is installed globally:

   ```bash
   mvn spring-boot:run
   ```

The backend should run at:

```text
http://localhost:8080
```

## Frontend Setup

1. Open a terminal in the project root and navigate to the frontend directory:

   ```bash
   cd frontend
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

3. Create a `.env` file in the frontend root directory:

   ```env
   VITE_BASE_URL="http://localhost:8080"
   ```

4. Start the development server:

   ```bash
   npm run dev
   ```

5. Open the frontend URL displayed in the terminal. Vite commonly uses:

   ```text
   http://localhost:5173
   ```

## Frontend Scripts

### Start Development Server

```bash
npm run dev
```

Starts the Vite development server.

### Build for Production

```bash
npm run build
```

Creates an optimized production build.

### Preview Production Build

```bash
npm run preview
```

Runs the production build locally.

### Run Linting

```bash
npm run lint
```

Runs ESLint if the project has a corresponding script configured in `package.json`.

## Environment Variables

Create this file:

```text
frontend/.env
```

Add the following variable:

```env
VITE_BASE_URL="http://localhost:8080"
```

If your backend runs on another port or domain, update the value and restart the Vite development server.

Example for a deployed API:

```env
VITE_BASE_URL="https://your-backend-domain.com"
```

## Multipart Product Requests

Products are sent using `multipart/form-data` because requests include both product data and an image file.

Example frontend request structure:

```javascript
const formData = new FormData();

formData.append(
  "product",
  new Blob([JSON.stringify(product)], {
    type: "application/json",
  })
);

formData.append("imageFile", imageFile);

await axios.post(`${baseUrl}/api/product`, formData);
```

For updates:

```javascript
await axios.put(`${baseUrl}/api/product/${id}`, formData);
```

Do not manually set the `Content-Type` header when sending `FormData` through Axios in the browser. Axios and the browser can set the appropriate multipart content type and boundary.

## Sample Product JSON

```json
{
  "name": "Sample Laptop",
  "brand": "Example Brand",
  "description": "A sample product description.",
  "price": 59999,
  "category": "Laptop",
  "stockQuantity": 10,
  "releaseDate": "2026-01-01",
  "productAvailable": true
}
```

## Sample Order JSON

```json
{
  "customerName": "Customer Name",
  "email": "customer@example.com",
  "items": [
    {
      "productId": 1,
      "quantity": 2
    }
  ]
}
```

## Screenshots

Add screenshots after running or deploying the application.

Suggested screenshot structure:

```text
screenshots/
├── home-page.png
├── product-details.png
├── add-product.png
├── search-results.png
├── cart.png
└── orders.png
```

Reference your screenshots in this section:

```markdown
![Home Page](./screenshots/home-page.png)

![Product Details](./screenshots/product-details.png)

![Cart](./screenshots/cart.png)
```

Make sure the screenshots exist at the specified paths before adding these references.

## Troubleshooting

### Frontend Cannot Connect to Backend

- Verify that the Spring Boot application is running.

- Check the backend URL:

  ```text
  http://localhost:8080
  ```

- Verify the `.env` value:

  ```env
  VITE_BASE_URL="http://localhost:8080"
  ```

- Restart Vite after changing environment variables.

- Check the browser console and backend logs for errors.

- Verify that the backend CORS configuration allows requests from the frontend origin.

### Search Returns No Results

Test the backend search endpoint:

```http
GET /api/products/search?keyword=laptop
```

Ensure the controller mapping, frontend request URL, and repository query match.

### Multipart Request Error

If the backend returns an error similar to:

```text
request doesn't contain a multipart/form-data stream
```

Verify that:

- The frontend sends `FormData`, not raw JSON.
- The backend accepts multipart requests.
- The multipart field names match the controller parameters.
- The request is not being sent with an incorrect manually configured `Content-Type` header.

### Update Returns HTTP 400

Check that:

- The request includes a `product` part.
- The multipart part name matches `@RequestPart("product")`, if that annotation is used.
- The image part is named `imageFile`.
- The backend allows `imageFile` to be optional if products can be updated without selecting a new image.
- The request body matches the backend's expected data types and validation rules.

## Future Improvements

- User registration and login
- JWT authentication and authorization
- Role-based admin access
- Product ratings and reviews
- Pagination and sorting
- Product filters
- Payment gateway integration
- Order-status updates
- Email confirmation after checkout
- Unit and integration tests
- Docker support
- CI/CD deployment pipeline

## Learning Outcomes

This project helped me practice:

- Java and object-oriented programming
- Spring Boot application development
- RESTful API design
- CRUD operations
- JPA and repository queries
- React component development
- React state and context management
- Axios API integration
- Multipart image upload handling
- Error handling and debugging
- Responsive frontend design
- Full-stack application architecture

## Author

**Karthik Sherigar**

- GitHub: [https://github.com/Karthik-sherigar](https://github.com/Karthik-sherigar)
- LinkedIn: [https://www.linkedin.com/in/karthik-sherigar/](https://www.linkedin.com/in/karthik-sherigar/)

## License

This project is created for learning and portfolio purposes.
