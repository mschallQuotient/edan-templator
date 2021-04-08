# edan-templator
A Simple Templator Application for handling EDAN Data

## How to run

1. Run Maven Install:
	> mvn clean install
2. Start Springboot:
	> ./mvnw spring-boot:run

The application should now be running on **localhost:8080**.

## Navigation

The application serves two types of content: *fragments* and *pages*. Fragments are components composed of EDAN content structured through use of Handlebars templates. These fragments are served without additional styling. Meanwhile, pages are compositions of EDAN content components served with custom styling.

Fragments can be accessed on the path:
> localhost:8080/fragment/{ template }/{ name }

In this schema, *template* is the name of the component type (i.e. paragraph, image, etc.). Meanwhile, *name* corresponds to the name of the specific json content file that populates the template.

NOTE: Do not include ".json" in *name*.

A comprehensive list of available fragments can be found under the following directory:
> src/main/resources/static/json/fragments

Pages can be accessed on the path:

> localhost:8080/page/{ pagename }

A comprehensive list of available fragments can be found under the following directory:
> src/main/resources/static/json/pages

## Components

The application defines nine distinct content components. The data for these components is stored as json and the structure of each components data is defined in a corresponding JSON Schema file.

### The available content components are as follows:

 - **Title:** A title component with the options of a title, subtitle, and background image.
 -  **Paragraph:** A chunk of html content.
 -  **Image:** An image component with optional caption and link.
 -  **Header:** A section header with configurable header level.
 -  **Link:** A simple link component.
 -  **Video:** A component displaying an embedded video with optional captions.
 -  **Quote:** A quote component with body and attribution.
 -  **Resources:** A series of sectioned resource lists.
 -  **Filmstrip:** A series of images with optional captions and links.

## Configuring Pages
Pages are configured using JSON files that define a list of components to display. Each component is of the following format:

    {
		"componentType": "Type of component to display",
		"edanUrl": "Name of component json file"
    }

NOTE: The property *edanUrl* currently only corresponds to the name of the component JSON file. However, in future iterations, this edanUrl would instead correspond to an EDAN object from which to draw content. The application would be provided an EDAN url and a component type and would proceed to generate appropriate JSON data from which an html fragment could be derived. Another possible expansion is an "options" property for the above JSON which defines certain rules for parsing and consolidating EDAN data into appropriate component JSON. 
