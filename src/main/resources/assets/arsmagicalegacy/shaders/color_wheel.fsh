#version 330

#define PI 3.14159265358979323846

in vec2 texCoord0;
in vec4 vertexColor;

out vec4 fragColor;

vec3 hsb2rgb(in vec3 c) {
    vec3 rgb = clamp(abs(mod(c.x * 6.0 + vec3(0.0, 4.0, 2.0), 6.0) - 3.0) - 1.0, 0.0, 1.0);
    return c.z * mix(vec3(1.0), rgb, c.y);
}

void main() {
    // Source brightness and outline from the repurposed alpha and red channels
    float brightness = vertexColor.a;
    bool outline = vertexColor.r == 1.0;
    // Calculate the distance of the pixel from the center of the circle
    float saturation = distance(texCoord0, vec2(0.0, 0.0));
    // If the pixel is outside the circle, set the color to transparent
    if (saturation > 1.0) {
        fragColor = vec4(0.0, 0.0, 0.0, 0.0);
        return;
    }
    // If we render the outline, set the color to white
    if (outline) {
        fragColor = vec4(1.0, 1.0, 1.0, 1.0);
        return;
    }
    // Calculate the angle of the pixel relative to the center of the circle and normalize it to the range [0, 1]
    float hue = (atan(-texCoord0.y, -texCoord0.x) + PI) / (PI * 2.0);
    // Calculate the RGB color for the hue and saturation
    vec3 rgb = hsb2rgb(vec3(hue, saturation, brightness));
    // Set the output color to the calculated RGB color
    fragColor = vec4(rgb, 1.0);
}
