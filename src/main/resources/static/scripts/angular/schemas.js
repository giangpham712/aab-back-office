angular.module("AAB.schemas", []);

angular.module("AAB.schemas")
    .config(function () {

        tv4.addLanguage('custom-en', {
            "OBJECT_REQUIRED": "{key} is required"
        });

        tv4.language('custom-en');

        tv4.addFormat('string-integer', function (data, schema) {

            if (typeof data === 'number' || (typeof data === 'string' && parseInt(data) != NaN)) {
                return null;
            }
            return "Not a valid integer";
        });

    });

angular.module("AAB.schemas")
    .value("ORDER_ITEM_SCHEMA", {
        "title": "Order Item Schema",
        "type": "object",
        "properties": {
            "productId": {
                "type": ["string", "integer"],
                "format": "string-integer",
            },
            "itemName": {
                "type": "string"
            },
            "thickness": {
                "type": ["string", "number"],
                "format": "string-number",
            },
            "width": {
                "type": ["string", "number"],
                "format": "string-number",
            },
            "blowingWidth": {
                "type": ["string", "number"],
                "format": "string-number",
            },
            "length": {
                "type": ["string", "number"],
                "format": "string-number",
            },
            "material": {
                "type": "string",
                "minLength": 1,
            },
            "filmColor": {
                "type": "string",
                "minLength": 1,
            },
            "printing": {
                "type": "string",

            },
            "quantity": {
                "type": ["string", "number"],
                "format": "string-integer",
            },
            "outerbagPrinting": {
                "type": "string",
            }

        },
        "required": ["productId", "itemName", "thickness", "width", "blowingWidth", "length", "material", "filmColor", "printing", "quantity", "outerbagPrinting",]
    })
    .value("ORDER_SCHEMA", {
        "title": "Order Schema",
        "type": "object",
        "properties": {

        },
    });